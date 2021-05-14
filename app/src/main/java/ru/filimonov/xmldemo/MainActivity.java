package ru.filimonov.xmldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View view) {
        XmlPullParser parser = getResources().getXml(R.xml.simple_rss);

        ArrayList<String> stringArrayList = new ArrayList<>();
        try {
            parser.next();
            String titleStr = "a", linkStr = "a", descrStr = "a";
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT){
                if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("item")){
                    parser.next();
                    if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("title")){
                        titleStr = parser.nextText();
                        parser.next();
                    }
                    if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("link")){
                        linkStr = parser.nextText();
                    }
                    int flag=0;
                    while (flag == 0) {
                        if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals("description")) {
                            parser.next();
                            descrStr = parser.getText();
                            parser.next();
                            flag=1;
                        }
                        else {parser.next();}
                    }
                    stringArrayList.add(String.format(getResources().getString(R.string.rss_template), titleStr, linkStr, descrStr));
                }
                parser.next();
            }
        }
        catch(XmlPullParserException e){
            Toast.makeText(this, "Возникла ошибка типа XmlPullParserException", Toast.LENGTH_SHORT);
        }
        catch(IOException e){

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stringArrayList);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}