package com.jbr.dailyfinance.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.TreeViewModel.DefaultNodeInfo;
import com.jbr.dailyfinance.client.CategoryComs;
import com.jbr.dailyfinance.client.HumanDate;
import com.jbr.dailyfinance.client.StoreComs;
import com.jbr.dailyfinance.client.TicketComs;
import com.jbr.dailyfinance.client.TicketDateComs;
import com.jbr.dailyfinance.client.TicketLineComs;
import com.jbr.dailyfinance.client.entities.CategoryImpl;
import com.jbr.dailyfinance.client.entities.StoreImpl;
import com.jbr.dailyfinance.client.entities.TicketDateImpl;
import com.jbr.dailyfinance.client.entities.TicketImpl;
import com.jbr.dailyfinance.client.entities.TicketLineImpl;
import com.jbr.gwt.json.client.JsonUtils;
import com.jbr.gwt.json.client.JsonUtils.ListCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jbr
 */
public class TicketsUI extends Composite {
    interface TicketsUIUiBinder extends UiBinder<Widget, TicketsUI> {
    }

    private static TicketsUIUiBinder uiBinder = GWT.create(TicketsUIUiBinder.class);
    private static TicketComs        tc       = new TicketComs();
    private static StoreComs         sc       = new StoreComs();
    private static TicketDateComs    tdc      = new TicketDateComs();
    private static TicketLineComs    tlc      = new TicketLineComs();
    private static CategoryComs      cc       = new CategoryComs();

    private List<CategoryImpl> categoryList = null;
    private List<StoreImpl>    storeList    = null;
    private Long               currentTicketId;

    @UiField(provided=true)
    CellTree ticketTree;

    @UiField(provided=true)
    CellTable<TicketLineImpl> ticketTable = new CellTable<TicketLineImpl>(200);;
    final SingleSelectionModel<TicketImpl> ticketTreeSelectionModel =
            new SingleSelectionModel<TicketImpl>();
    final SingleSelectionModel<TicketLineImpl> ticketLineSelectionModel =
            new SingleSelectionModel<TicketLineImpl>();
    public static final NumberFormat CURRENCYFORMAT = NumberFormat.getCurrencyFormat();
    public static final NumberFormat DECIMALFORMAT = NumberFormat.getFormat("#0.00");

    @UiField
    Label ticketSum = new Label();

    @UiField
    ListBox categoryListBox = new ListBox();

    @UiField
    TextBox amountTextBox = new TextBox();

    @UiField
    TextBox picePriceTextBox = new TextBox();

    @UiField
    TextBox numberTextBox = new TextBox();

    @UiField
    Button saveButton;

    @UiField
    Button newButton;

    @UiField
    Button createButton;

    @UiField
    Button saveStoreButton;

    @UiField
    Button newTicketButton;

    @UiField
    Button deleteTicketButton;

    @UiField
    DateBox ticketDateBox;

    @UiField
    ListBox storeListBox;

    @UiField
    SplitLayoutPanel ticketDetailsPanel;

    @UiHandler("saveButton")
    public void handleSaveButton(ClickEvent e) {
        System.out.println("Save Button pressed");
        updateFromEditForm(ticketLineSelectionModel.getSelectedObject(),
                        getSelectedTicketLineIndex());
        categoryListBox.setFocus(true);
    }

    @UiHandler("newButton")
    public void handleNewButton(ClickEvent e) {
        System.out.println("New Button pressed");
        updateFromEditForm((TicketLineImpl)TicketLineImpl.createObject(),
                        ticketTable.getVisibleItems().size());
        categoryListBox.setFocus(true);
    }

    @UiHandler("createButton")
    public void handleCreateButton(ClickEvent e) {
        System.out.println("Create Button pressed");
        TicketImpl ti = (TicketImpl) TicketImpl.createObject();
        ti.setStoreId(storeList.get(storeListBox.getSelectedIndex()).getId());
        ti.setTicketDate(ticketDateBox.getValue());
        tc.setTicketDate(ticketDateBox.getValue());
        tc.put(0, ti, new JsonUtils.ElementCallback<TicketImpl, Integer>() {

            @Override
            public void onResponseOk(TicketImpl element, Integer backref) {
                currentTicketId = element.getId();
                updateTicketTable();
                deleteTicketButton.setEnabled(true);
                saveStoreButton.setEnabled(true);
                newTicketButton.setEnabled(true);
                createButton.setEnabled(false);
                ticketDetailsPanel.setVisible(true);
                categoryListBox.setFocus(true);
            }
        });
    }

    @UiHandler("saveStoreButton")
    public void handleSaveStoreButton(ClickEvent e) {
        System.out.println("Save store Button pressed");
        TicketImpl ti = ticketTreeSelectionModel.getSelectedObject();
        ti.setStoreId(storeList.get(storeListBox.getSelectedIndex()).getId());
        ti.setTicketDate(ticketDateBox.getValue());
        tc.setTicketDate(ticketDateBox.getValue());
        tc.put(0, ti, new JsonUtils.ElementCallback<TicketImpl, Integer>() {

            @Override
            public void onResponseOk(TicketImpl element, Integer backref) {
                
            }
        });
    }

    @UiHandler("deleteTicketButton")
    public void handleDeleteButton(ClickEvent e) {
        System.out.println("Delete ticket Button pressed");
        if (!Window.confirm("Vil du slette denne bon?"))
            return;
        tc.delete(currentTicketId, new JsonUtils.RemoveCallback() {

            @Override
            public void onResponseOk() {
                ticketDetailsPanel.setVisible(false);
                closeNodesTicketTree();
            }
        });
    }

    @UiHandler("newTicketButton")
    public void handlerNewTicketButton(ClickEvent e) {
        System.out.println("New ticket");
        ticketDateBox.setValue(new Date());
        createButton.setEnabled(true);
        deleteTicketButton.setEnabled(false);
        saveStoreButton.setEnabled(false);
        ticketDateBox.setFocus(true);
        ticketDetailsPanel.setVisible(false);
        newTicketButton.setEnabled(false);
        ticketDateBox.setFocus(true);
    }

    @UiHandler("numberTextBox")
    public void handleNumberKeyPress(KeyUpEvent e) {
        submitEnter(e);
        updateAmountTextBox();
    }

    @UiHandler("picePriceTextBox")
    public void handlePicePriceKeyPress(KeyUpEvent e) {
        submitEnter(e);
        updateAmountTextBox();
    }

    @UiHandler("amountTextBox")
    public void handleAmountKeyPress(KeyUpEvent e) {
        submitEnter(e);
    }

    final AsyncDataProvider<TicketLineImpl> ticketLineProvider =
            new AsyncDataProvider<TicketLineImpl>() {
        @Override
        protected void onRangeChanged(HasData<TicketLineImpl> display) {
            if (currentTicketId == null) {
                updateRowCount(0, true);
                return;
            }
            tlc.setTicketId(currentTicketId);
            tlc.list(new ListCallback<TicketLineImpl>() {

                @Override
                public void onResponseOk(List<TicketLineImpl> ticketLineList) {
                    int rowNum=0;
                    for (final TicketLineImpl t : ticketLineList) {
                        final Integer finalRowNum = rowNum;
                        cc.setCategoryId(t.getCategoryId());
                        cc.list(new ListCallback<CategoryImpl>() {

                            @Override
                            public void onResponseOk(List<CategoryImpl> category) {
                                ArrayList<TicketLineImpl> singleTicketLine =
                                        new ArrayList<TicketLineImpl>(1);
                                t.setCategoryName(category.get(0).getName());
                                singleTicketLine.add(t);
                                System.out.println("CategoryName sat to  " + t.getCategoryName());
                                updateRowData(finalRowNum, singleTicketLine);
                                updateSumOnTicket();
                            }
                        });
                        rowNum++;
                    }
                    updateRowCount(ticketLineList.size(), true);
                }
            });
        }
    };

    public void updateAmountTextBox() throws NumberFormatException {
        System.out.println("Number changed");
        double number = Double.parseDouble(numberTextBox.getText());
        double price = DECIMALFORMAT.parse(picePriceTextBox.getText());
        amountTextBox.setText(DECIMALFORMAT.format(number * price));
    }

    public void submitEnter(KeyUpEvent e) {
        if (e.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            if (ticketLineSelectionModel.getSelectedObject()==null)
                handleNewButton(null);
            else
                handleSaveButton(null);
        }

    }

    private void updateTicketHeader(TicketImpl t) {
        ticketDateBox.setValue(t.getTicketDate());
        storeListBox.setSelectedIndex(indexOf(storeList, t.getStoreId()));
    }

    public TicketsUI() {
        ticketTreeSelectionModel.addSelectionChangeHandler(
                new SelectionChangeEvent.Handler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                if (ticketTreeSelectionModel.getSelectedObject() == null) {
                    ticketDetailsPanel.setVisible(false);
                }

                System.out.println("Selected: " + ticketTreeSelectionModel.getSelectedObject().getId());
                currentTicketId = ticketTreeSelectionModel.getSelectedObject().getId();

                updateTicketHeader(ticketTreeSelectionModel.getSelectedObject());
                ticketDetailsPanel.setVisible(true);
                deleteTicketButton.setEnabled(true);
                createButton.setEnabled(false);
                newTicketButton.setEnabled(true);
                saveStoreButton.setEnabled(true);
                updateTicketTable();
            }

        });

        ticketLineSelectionModel.addSelectionChangeHandler(new Handler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                System.out.println("Selected ticketlineid: " +
                        ticketLineSelectionModel.getSelectedObject().getIdRaw());
                updateToEditForm();
            }
        });

        ticketTree = new CellTree(new TreeViewModel() {

            @Override
            public <T> NodeInfo<?> getNodeInfo(T value) {

                if (value == null) {
                    AsyncDataProvider<TicketDateImpl> provider = new AsyncDataProvider<TicketDateImpl>() {

                        @Override
                        protected void onRangeChanged(HasData<TicketDateImpl> display) {
                            final Range range = display.getVisibleRange();
                            System.out.println("Requesting update of tree range " + range.toString());
                            tdc.list(range.getStart(), range.getLength(),
                                    new ListCallback<TicketDateImpl>() {

                                @Override
                                public void onResponseOk(List<TicketDateImpl> list) {
                                    for (TicketDateImpl t : list) {
                                        System.out.println(t.toJson());
                                        System.out.println(t.getTicketDate());
                                    }

                                    updateRowCount(10000, false);
                                    updateRowData(range.getStart(), list);
                                }
                            });

                        }
                    };
                    return new DefaultNodeInfo<TicketDateImpl>(provider, new AbstractCell<TicketDateImpl>() {

                        @Override
                        public void render(Context context, TicketDateImpl value, SafeHtmlBuilder sb) {
                            sb.appendEscaped(value.getTicketDateAsString());
                        }
                    });
                } else if (value instanceof TicketDateImpl) {
                    final TicketDateImpl tdi = (TicketDateImpl)value;
                    AsyncDataProvider<TicketImpl> provider = new AsyncDataProvider<TicketImpl>() {

                        @Override
                        protected void onRangeChanged(HasData<TicketImpl> display) {
                            final Range range = display.getVisibleRange();
                            System.out.println("Requesting update of tree range " + range.toString());
                            tc.setTicketDate(tdi.getTicketDate());
                            tc.list(range.getStart(), range.getLength(), new ListCallback<TicketImpl>() {

                                @Override
                                public void onResponseOk(final List<TicketImpl> ticketList) {
                                    updateRowCount(ticketList.size(), true);
                                    updateRowData(range.getStart(), ticketList);
                                }
                            });
                        }
                    };
                    return new DefaultNodeInfo<TicketImpl>(provider, new AbstractCell<TicketImpl>() {

                        @Override
                        public void render(Context context, TicketImpl value, SafeHtmlBuilder sb) {
                            if (value.getStoreName() == null)
                                sb.appendEscaped("(henter butik...)");
                            else
                                sb.appendEscaped(value.getStoreName());
                        }
                    }, (SelectionModel)ticketTreeSelectionModel, new ValueUpdater<TicketImpl>() {

                        @Override
                        public void update(TicketImpl value) {
                            System.out.println("Update " + value + " not supported");
                        }
                    });
                } else
                    return null;
            }

            @Override
            public boolean isLeaf(Object value) {
                if (value == null)
                    return false;
                if (value instanceof JavaScriptObject) {
                    final JavaScriptObject jso = (JavaScriptObject)value;
                    System.out.println("JsonUtils.getObjectName(jso)=" + JsonUtils.getObjectName(jso));
                    if (JsonUtils.getObjectName(jso).equalsIgnoreCase("ticketDate")) {
                        System.out.println("Leaf of ticketdateimpl" + ((TicketDateImpl)value).getTicketDateAsString());
                        return false;
                    }
                    if (JsonUtils.getObjectName(jso).equalsIgnoreCase("ticket")) {
                        System.out.println("Leaf of ticket" + ((TicketImpl)value).getTicketDateAsString());
                        return true;
                    }
                }
                return value == null ? false : true;
            }
        },null);

        ticketTree.setAnimationEnabled(true);
        ticketTable.addColumn(new Column<TicketLineImpl, String>(new TextCell()) {

            @Override
            public String getValue(TicketLineImpl object) {
                return object.getCategoryName();
            }
        }, "Varekategori");
        final Column<TicketLineImpl, Number> numberCol = 
                new Column<TicketLineImpl, Number>(new NumberCell(DECIMALFORMAT)) {

            @Override
            public Double getValue(TicketLineImpl object) {
                return object.getAmount();
            }
        };
        numberCol.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        

        ticketTable.addColumn(numberCol, "Beløb");

        ticketTable.addColumn(new Column<TicketLineImpl, TicketLineImpl>(
                new ActionCell("Slet", new Delegate<TicketLineImpl>() {

            @Override
            public void execute(TicketLineImpl object) {
                if (Window.confirm("Er du sikker på du vil slette?")) {

                    tlc.setTicketId(currentTicketId);
                    tlc.delete(object.getId(), new JsonUtils.RemoveCallback() {

                        @Override
                        public void onResponseOk() {
                            updateTicketTable();
                        }

                    });
                }
            }
        })) {

            @Override
            public TicketLineImpl getValue(TicketLineImpl object) {
                return object;
            }
        }, "Slet");

        ticketLineProvider.addDataDisplay(ticketTable);
        ticketTable.setSelectionModel(ticketLineSelectionModel);

        cc.list(new ListCallback<CategoryImpl>() {

            @Override
            public void onResponseOk(List<CategoryImpl> list) {
                categoryList = list;
                updateCategoryListBox();
            }
        });

        sc.setStoreId(null);
        sc.list(new ListCallback<StoreImpl>() {

            @Override
            public void onResponseOk(List<StoreImpl> list) {
                storeList = list;
                updateStoreListBox();
            }
        });

        initWidget(uiBinder.createAndBindUi(this));
        ticketDateBox.setFormat(new DateBox.DefaultFormat(HumanDate.getFormat()));
        ticketDateBox.setValue(new Date());
    }

    private void updateCategoryListBox() {
        if (categoryList == null)
            return;
        categoryListBox.clear();
        for (CategoryImpl categoryImpl : categoryList) {
            categoryListBox.addItem(categoryImpl.getName(), categoryImpl.getIdRaw());
        }
    }

    private void updateStoreListBox() {
        if (storeList == null)
            return;
        storeListBox.clear();
        for (StoreImpl store : storeList) {
            storeListBox.addItem(store.getName(), store.getIdRaw());
        }
    }

    private void updateToEditForm() {
        final TicketLineImpl tl  = ticketLineSelectionModel.getSelectedObject();
        Window.alert("tl=" + tl);
        if (tl == null) {
            saveButton.setVisible(false);
            newButton.setVisible(true);
        } else {
            amountTextBox.setText(DECIMALFORMAT.format(tl.getAmount()));
            numberTextBox.setText(tl.getNumberRaw());
            picePriceTextBox.setText(amountTextBox.getText());
            categoryListBox.setSelectedIndex(indexOf(categoryList, tl.getCategoryId()));
            saveButton.setVisible(true);
            //newButton.setVisible(false);
        }
    }

    private void updateFromEditForm(final TicketLineImpl ctl, int placeIndex) {
        ctl.setAmount(DECIMALFORMAT.parse(amountTextBox.getText()));
        ctl.setNumber(new Integer(numberTextBox.getText()));
        ctl.setCategoryId(Long.parseLong(categoryListBox.getValue(categoryListBox.getSelectedIndex())));
        ctl.setTicketId(currentTicketId);
        System.out.println("Updateting ticketline: " + ctl.toJson());

        tlc.put(placeIndex, ctl, new JsonUtils.ElementCallback<TicketLineImpl, Integer>() {

            @Override
            public void onResponseOk(final TicketLineImpl element, final Integer backref) {
                cc.setCategoryId(element.getCategoryId());
                cc.list(new ListCallback<CategoryImpl>() {

                    @Override
                    public void onResponseOk(List<CategoryImpl> list) {
                        element.setCategoryName(list.get(0).getName());
                        ticketLineProvider.updateRowData(backref, Arrays.asList(element));
                        numberTextBox.setText("1");
                        updateSumOnTicket();
                    }
                });
            }
        });
    }

    public void updateTicketTable() {
        ticketTable.setVisibleRangeAndClearData(new Range(0, 10000), true);
    }

    public void closeNodesTicketTree() {
        for (int i= 0; i < ticketTree.getRootTreeNode().getChildCount(); i++) {
            ticketTree.getRootTreeNode().setChildOpen(i, false);
        }
    }

    public int getSelectedTicketLineIndex() {
        for (int i= 0; i < ticketTable.getVisibleItems().size(); i++) {
            if (ticketTable.getVisibleItems().get(i).getId()
                    .equals(ticketLineSelectionModel.getSelectedObject().getId()))
                return i;
        }
        return -1;
    }

    private <T extends JavaScriptObject> int indexOf(List<T> list, Long idchosen) {
        int i=0;
        for (T it : list) {
            if (((StoreImpl)it).getId().equals(idchosen))
                return i;
            i++;
        }
        return -1;
    }

    public void updateSumOnTicket() {
        double sum = 0d;
        System.out.println("Get sum on ticket. Visible Items:");
        for (TicketLineImpl t : ticketTable.getVisibleItems()) {
            System.out.println(t.getCategoryName() + " - " + t.getAmount());
            sum = sum + t.getAmount().doubleValue();
        }
        ticketSum.setText(CURRENCYFORMAT.format(sum));
    }

    public void ticketDetailsVisible(boolean visible) {
        
        
    }


}