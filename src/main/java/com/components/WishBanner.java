package com.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Import(stylesheet = "mybootstrap/css/birthday.css")
public class WishBanner {
        static private String LINE_SEPARATOR = System.getProperty("line.separator");

        static private String STYLE_FEMALEDISPLAY = "femaledisplay";
        static private String STYLE_MALEDISPLAY = "maledisplay";
        static private String STYLE_OTHERDISPLAY = "otherdisplay";
        static private String STYLE_SOURCE = "source";
        static private String STYLE_NOT_FOUND = "not-found";

        static private int TAB_STOPS_WIDTH = 4;

        // The source file path from the project root eg. "/web/src/main/jumpstart/web/pages/Start.java"
        @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
        private String src;

        @Inject
        private Context context;

        boolean beginRender(MarkupWriter writer) {

            // Print start of the source block

            writer.write(LINE_SEPARATOR);
            writer.writeRaw("<!-- Start of source code inserted by SourceCodeDisplay component. -->");
            writer.write(LINE_SEPARATOR);
            writer.write(LINE_SEPARATOR);

            // Print a div with style info to make a pretty block

            writer.element("div", "class", STYLE_FEMALEDISPLAY);
            {

                // Print the source

                if (src != null) {
                    printSourceFromInputStream(writer, src, "/WEB-INF/mybootstrap/css/birthday.css" + src);
                }

                // Print end of div

            }
            writer.end();

            // Print end of source block

            writer.write(LINE_SEPARATOR);
            writer.write(LINE_SEPARATOR);
            writer.writeRaw("<!-- End of source code inserted by SourceCodeDisplay component. -->");
            writer.write(LINE_SEPARATOR);

            return true;
        }

        private void printSourceFromInputStream(MarkupWriter writer, String title, String givenPath) {
            if (givenPath != null) {
                URL url = context.getResource(givenPath);
                try {
                    if (url != null) {
                        InputStream templateStream = url.openStream();
                        if (templateStream != null) {
                            BufferedReader templateReader = new BufferedReader(new InputStreamReader(templateStream));
                            printSource(writer, templateReader);
                        }
                        else {
                            printResourceNotFound(writer, givenPath);
                        }
                    }
                    else {
                        printResourceNotFound(writer, givenPath);
                    }
                }
                catch (IOException e) {
                    printResourceNotFound(writer, givenPath);
                }
            }
        }

        private void printSource(MarkupWriter writer, BufferedReader sourceReader) {
            writer.element("div", "class", STYLE_SOURCE);
            {
                writer.element("pre");
                {
                    writer.write(LINE_SEPARATOR);
                    writer.element("code");
                    {
                        writer.write(LINE_SEPARATOR);

                        String s;
                        try {
                            while ((s = sourceReader.readLine()) != null) {
                                s = replaceTabsWithSpaces(s);
                                writer.write(s);
                                writer.write(LINE_SEPARATOR);
                            }
                        }
                        catch (IOException e) {
                            writer.write("Error reading .....?");
                            e.printStackTrace();
                        }

                    }
                    writer.end();
                    writer.write(LINE_SEPARATOR);
                }
                writer.end();
            }
            writer.end();
        }

        private void printResourceNotFound(MarkupWriter writer, String resourcePath) {
            writer.element("div", "class", STYLE_NOT_FOUND);
            {
                writer.write("The file was not found. Path given was " + resourcePath);
            }
            writer.end();
        }

        private String replaceTabsWithSpaces(String s) {
            StringBuilder sb = new StringBuilder();
            char c;
            int column = 1;

            for (int i = 0; i < s.length(); i++, column++) {
                if ((c = s.charAt(i)) == '\t') {
                    sb.append(' ');
                    while (column % TAB_STOPS_WIDTH != 0) {
                        sb.append(' ');
                        column++;
                    }
                }
                else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }


