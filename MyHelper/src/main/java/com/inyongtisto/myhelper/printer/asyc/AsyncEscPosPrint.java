package com.inyongtisto.myhelper.printer.asyc;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dantsu.escposprinter.EscPosCharsetEncoding;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import java.lang.ref.WeakReference;

public abstract class AsyncEscPosPrint extends AsyncTask<AsyncEscPosPrinter, Integer, AsyncEscPosPrint.PrinterStatus> {
    public final static int FINISH_SUCCESS = 1;
    public final static int FINISH_NO_PRINTER = 2;
    public final static int FINISH_PRINTER_DISCONNECTED = 3;
    public final static int FINISH_PARSER_ERROR = 4;
    public final static int FINISH_ENCODING_ERROR = 5;
    public final static int FINISH_BARCODE_ERROR = 6;

    public final static int PROGRESS_CONNECTING = 1;
    public final static int PROGRESS_CONNECTED = 2;
    public final static int PROGRESS_PRINTING = 3;
    public final static int PROGRESS_PRINTED = 4;

    protected ProgressDialog dialog;
    protected WeakReference<Context> weakContext;
    protected OnPrintFinished printChangeListener;

    public AsyncEscPosPrint(Context context, OnPrintFinished onPrintFinished) {
        this.weakContext = new WeakReference<>(context);
        this.printChangeListener = onPrintFinished;
    }

    protected PrinterStatus doInBackground(AsyncEscPosPrinter... printersData) {
        if (printersData.length == 0) {
            return new PrinterStatus(null, AsyncEscPosPrint.FINISH_NO_PRINTER);
        }

        this.publishProgress(AsyncEscPosPrint.PROGRESS_CONNECTING);

        AsyncEscPosPrinter printerData = printersData[0];

        try {
            DeviceConnection deviceConnection = printerData.getPrinterConnection();

            if (deviceConnection == null) {
                return new PrinterStatus(null, AsyncEscPosPrint.FINISH_NO_PRINTER);
            }

            EscPosPrinter printer = new EscPosPrinter(
                    deviceConnection,
                    printerData.getPrinterDpi(),
                    printerData.getPrinterWidthMM(),
                    printerData.getPrinterNbrCharactersPerLine(),
                    new EscPosCharsetEncoding("windows-1252", 16)
            );

            // printer.useEscAsteriskCommand(true);

            this.publishProgress(AsyncEscPosPrint.PROGRESS_PRINTING);

            String[] textsToPrint = printerData.getTextsToPrint();

            for (String textToPrint : textsToPrint) {
                printer.printFormattedTextAndCut(textToPrint);
                Thread.sleep(500);
            }

            this.publishProgress(AsyncEscPosPrint.PROGRESS_PRINTED);

        } catch (EscPosConnectionException e) {
            e.printStackTrace();
            return new PrinterStatus(printerData, AsyncEscPosPrint.FINISH_PRINTER_DISCONNECTED);
        } catch (EscPosParserException e) {
            e.printStackTrace();
            return new PrinterStatus(printerData, AsyncEscPosPrint.FINISH_PARSER_ERROR);
        } catch (EscPosEncodingException e) {
            e.printStackTrace();
            return new PrinterStatus(printerData, AsyncEscPosPrint.FINISH_ENCODING_ERROR);
        } catch (EscPosBarcodeException e) {
            e.printStackTrace();
            return new PrinterStatus(printerData, AsyncEscPosPrint.FINISH_BARCODE_ERROR);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new PrinterStatus(printerData, AsyncEscPosPrint.FINISH_SUCCESS);
    }

    protected void onPreExecute() {
        if (this.dialog == null) {
//            Context context = weakContext.get();
//
//            if (context == null) {
//                return;
//            }
//
//            this.dialog = new ProgressDialog(context);
//            this.dialog.setTitle("Printing in progress...");
//            this.dialog.setMessage("...");
//            this.dialog.setProgressNumberFormat("%1d / %2d");
//            this.dialog.setCancelable(false);
//            this.dialog.setIndeterminate(false);
//            this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            this.dialog.show();
        }
    }

    protected void onProgressUpdate(Integer... progress) {
        switch (progress[0]) {
            case AsyncEscPosPrint.PROGRESS_CONNECTING:
                printChangeListener.onProgressChangeListener(AsyncEscPosPrint.PROGRESS_CONNECTING);
                break;
            case AsyncEscPosPrint.PROGRESS_CONNECTED:
                printChangeListener.onProgressChangeListener(AsyncEscPosPrint.PROGRESS_CONNECTED);
                break;
            case AsyncEscPosPrint.PROGRESS_PRINTING:
                printChangeListener.onProgressChangeListener(AsyncEscPosPrint.PROGRESS_PRINTING);
                break;
            case AsyncEscPosPrint.PROGRESS_PRINTED:
                printChangeListener.onProgressChangeListener(AsyncEscPosPrint.PROGRESS_PRINTED);
                break;
        }
    }

    protected void onPostExecute(PrinterStatus result) {
        Context context = weakContext.get();

        if (context == null) {
            return;
        }

        if (this.printChangeListener != null) {
            if (result.getPrinterStatus() == AsyncEscPosPrint.FINISH_SUCCESS) {
                this.printChangeListener.onSuccess(result.getAsyncEscPosPrinter());
            } else {
                String message = "No printer";
                switch (result.getPrinterStatus()) {
                    case AsyncEscPosPrint.FINISH_SUCCESS:
                        message = "Success";
                        break;
                    case AsyncEscPosPrint.FINISH_NO_PRINTER:
                        message = "No printer";
                        break;
                    case AsyncEscPosPrint.FINISH_PRINTER_DISCONNECTED:
                        message = "Broken connection";
                        break;
                    case AsyncEscPosPrint.FINISH_PARSER_ERROR:
                        message = "Invalid formatted text";
                        break;
                    case AsyncEscPosPrint.FINISH_ENCODING_ERROR:
                        message = "Bad selected encoding";
                        break;
                    case AsyncEscPosPrint.FINISH_BARCODE_ERROR:
                        message = "Invalid barcode";
                        break;
                }
                this.printChangeListener.onError(result.getAsyncEscPosPrinter(), result.getPrinterStatus(), message);
            }
        }
    }

    public static class PrinterStatus {
        private final AsyncEscPosPrinter asyncEscPosPrinter;
        private final int printerStatus;

        public PrinterStatus(AsyncEscPosPrinter asyncEscPosPrinter, int printerStatus) {
            this.asyncEscPosPrinter = asyncEscPosPrinter;
            this.printerStatus = printerStatus;
        }

        public AsyncEscPosPrinter getAsyncEscPosPrinter() {
            return asyncEscPosPrinter;
        }

        public int getPrinterStatus() {
            return printerStatus;
        }
    }

    public static abstract class OnPrintFinished {
        public abstract void onError(AsyncEscPosPrinter asyncEscPosPrinter, int codeException, String message);

        public abstract void onSuccess(AsyncEscPosPrinter asyncEscPosPrinter);

        public abstract void onProgressChangeListener(int status);
    }
}
