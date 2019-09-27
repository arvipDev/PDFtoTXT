import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PDFHome {

    public static void main(String[] args) {
        PDFManager pdfManager = new PDFManager();
        pdfManager.setFilePath("C:\\Users\\ARVIND\\Desktop\\investment.pdf");
        try {
            String text = pdfManager.toText();
            writeUsingBufferedWriter(text, 999);
            System.out.println(text);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void writeUsingBufferedWriter(String data, int noOfLines) {
        File file = new File("C:\\Users\\ARVIND\\Desktop\\investment.txt");
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=data+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for(int i = noOfLines; i>0; i--){
                br.write(dataWithNewLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
