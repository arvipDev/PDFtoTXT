package arvindsafran;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class pdf_txt implements ActionListener{

	private JFrame frame;
	private JButton choose_btn, convert_btn;
	JTextArea browse_text;
	ArrayList<String> pdfs = new ArrayList<String>();
	ArrayList<String> pdfs_path = new ArrayList<String>();
	String parent_direc;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pdf_txt window = new pdf_txt();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
				
	}

	public pdf_txt() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("PDF to TXT Converter Version 1");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		choose_btn = new JButton("Choose PDF");
		choose_btn.setBounds(167, 193, 124, 23);
		desktopPane.add(choose_btn);
		choose_btn.addActionListener(this);
		
		convert_btn = new JButton("Convert");
		convert_btn.setBounds(167, 227, 124, 23);
		desktopPane.add(convert_btn);
		convert_btn.addActionListener(this);
		
		browse_text = new JTextArea();
		browse_text.setBackground(Color.LIGHT_GRAY);
		browse_text.setBounds(10, 11, 414, 171);
		desktopPane.add(browse_text);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == choose_btn) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Multiple file and directory selection:");
			jfc.setMultiSelectionEnabled(true);
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File[] files = jfc.getSelectedFiles();
				System.out.println("Directories found\n");
				Arrays.asList(files).forEach(x -> {
					if (x.isDirectory()) {
						System.out.println(x.getName());
					}
				});
				System.out.println("\n- - - - - - - - - - -\n");
				System.out.println("Files Found\n");
				Arrays.asList(files).forEach(x -> {
					if (x.isFile()) {
						System.out.println(x.getName());
						pdfs.add(x.getName());
						pdfs_path.add(x.getAbsolutePath());
						//parent_direc = x.getAbsoluteFile().getParent();
						System.out.println("test " + x.getAbsoluteFile().getParent() + "\n");						
						System.out.println("in list " + pdfs.get(pdfs.size() - 1));
						System.out.println("in list abso path " + pdfs_path.get(pdfs.size() - 1));
					}
				});
			}
			String pdf_names = null;
			for (String pdf_name : pdfs) {
				if(pdf_names == null) { pdf_names = pdf_name; }
				else { pdf_names = pdf_names + "\r\n" + pdf_name; }
			} browse_text.setText(pdf_names);
		}if(e.getSource() == convert_btn) {
			PDFManager pdfManager = new PDFManager();
			//int counter = 0;
			//String txt;
			for (String pdf_path : pdfs_path) {
				pdfManager.setFilePath(pdf_path);
		        try {
		        	//txt = pdfs.get(counter++);
		        	//parent_direc = parent_direc + txt.substring(0, txt.length()-3) + "txt";		   
		        	parent_direc = pdf_path.substring(0, pdf_path.length() - 3) + ".txt";
		        	System.out.println("\n text " + parent_direc);
		            String text = pdfManager.toText();
		            writeUsingBufferedWriter(text, 999, parent_direc);
		            System.out.println(text);
		        } catch (IOException ex) {
		            System.err.println(ex.getMessage());
		        }
			}
		}
		
	}
	
	private static void writeUsingBufferedWriter(String data, int noOfLines, String abso_path) {
        File file = new File(abso_path);
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
