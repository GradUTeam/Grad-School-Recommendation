package com.example.pranshu.gradureco;

import android.content.Context;

import com.example.pranshu.gradureco.activity.ApplyFilterActivity;
import com.example.pranshu.gradureco.app.AppController;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ApplyFilterTest {

//    private static final String FAKE_STRING = "HELLO WORLD";
//
//
//    Context mMockContext;

    @Test(expected = org.mockito.exceptions.base.MockitoException.class)
    public void readStringFromContext_LocalizedString() throws IOException {


        ApplyFilterActivity mockStream = Mockito.mock(ApplyFilterActivity.class);


        doThrow(new IOException()).when(mockStream).Apply_filter("1", "2", "3", "4", "dsf");
        //Mockito.verify(mockStream).Apply_filter(new String("1"), new String("2"), new String("3"), new String("4"), new String("dsf"));
      
    }
}