package com.wat.tabularasa20;

import android.app.Activity;

import com.wat.tabularasa20.data.ProductListAdapter;
import com.wat.tabularasa20.utilities.Network;

import org.junit.Test;
import org.mockito.Mockito;

public class ProductListAdapterTest {

    @Test
    public void onBindViewHolderTest() {
        ProductListAdapter prodListAdapter = Mockito.mock(ProductListAdapter.class, Mockito.RETURNS_DEEP_STUBS);
        ProductListAdapter.ViewHolder viewHolder = Mockito.mock(ProductListAdapter.ViewHolder.class);
        prodListAdapter.onBindViewHolder(viewHolder, 1);
    }


}