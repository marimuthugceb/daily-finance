/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbr.dailyfinance.client.ui;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import java.util.ArrayList;

/**
 *
 * @author jbr
 */
public class TicketsUI extends Composite {

    private static TicketsUIUiBinder uiBinder = GWT.create(TicketsUIUiBinder.class);

    @UiField(provided=true)
    CellTree ticketTree;

    interface TicketsUIUiBinder extends UiBinder<Widget, TicketsUI> {
    }

    public TicketsUI() {
        ticketTree = new CellTree(new TreeViewModel() {

            public <T> NodeInfo<?> getNodeInfo(T value) {

                final ArrayList<String> treeData = new ArrayList<String>();
                treeData.add("31.05.2011");
                treeData.add("30.05.2011");
                treeData.add("29.05.2011");
                treeData.add("28.05.2011");
                treeData.add("20.05.2011");
                return new DefaultNodeInfo<String>(
                        new ListDataProvider<String>(treeData), new TextCell());
            }

            public boolean isLeaf(Object value) {
                System.out.println("Is leaf of: " + value);
                return value == null ? false : true;
            }
        },null);
        initWidget(uiBinder.createAndBindUi(this));
    }
}