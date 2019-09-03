import java.awt.event.ActionEvent;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.ListView;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

public class Database extends JFrame implements ActionListener {
	
	//JLabel viewTitle = new JLabel("Saved Addresses");
	
	JPanel inputField = new JPanel();
	JPanel viewField = new JPanel();
	
	input section = new input();
//	addressViewer view = new addressViewer();
	
	//Border border = new Border();
	private String first;
	private String last;
	private String address;
	private String state;
	private String city;
	
	Database(){
		this.setSize(800,400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		//viewField.add(viewTitle);
		//viewField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setLayout(new GridLayout());
		this.add(section);
		//this.add(view);
		
		this.setVisible(true);
		
		//view.read();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("Main");
		
	}

}
class input extends JPanel implements ActionListener{
	//JLabel label = new JLabel("Information");
	Comparator<Entry> compare = new ItemComparator("last");
	Vector<Entry> entries = new Vector();
	GridLayout grid = new GridLayout(0,2);
	addressViewer view;
	String title = "Information";
	Border border = BorderFactory.createTitledBorder(title);
	TextField first, last, address, city, state, zip;
	JButton addInfo = new JButton("Add");
	JLabel f = new JLabel("First: ");
	JLabel l = new JLabel("Last: ");
	JLabel a = new JLabel("Address: ");
	JLabel c = new JLabel("City: ");
	JLabel s = new JLabel("State: ");
	JLabel z = new JLabel("Zip Code: ");
	String getZip() {
		return zip.getText();
	}
	String getState() {
		return state.getText();
	}
	String getCity() {
		return city.getText();
	}
	String getAddress() {
		return address.getText();
	}
	String getLast() {
		return last.getText();
	}
	String getFirst() {
		return first.getText();
	}
	//TextPrompt tpName = new TextPrompt("First Name", name);
	input(){
		try {
			FileReader fr = new FileReader("AddressBook.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[]words = null;
			while((line = br.readLine()) != null) {
				words = line.split("[\t]");
				
				/*for(String obj : words) {
					System.out.println(obj);
				}*/
				String[] names = words[0].split(", ");
				String[] location = words[2].split(", ");
				entries.add(new Entry(names[1], names[0], words[1], location[0], location[1], words[3]));
				Collections.sort(entries, compare);
				System.out.println("////////////////////");
				for(Entry obj : entries) {
					obj.print();
				}				
				System.out.println("////////////////////");

				//System.out.println(names[0]+ names[1]+ words[1]+ location[0]+ location[1]+ words[3]);
			}
			
			/*for(String obj : names) {
				System.out.println(obj);
			}*/
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PrintWriter pw = new PrintWriter("AddressBook.txt");
			pw = new PrintWriter(new FileWriter("AddressBook.txt",true));
			for(Entry obj : entries) {
				pw.print(obj.returnAddress() + "\n");
			}
			pw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.setBorder(border);
		//this.setLayout(new FlowLayout(FlowLayout.LEFT));
		view = new addressViewer();
		first = new TextField(15);
		last = new TextField(15);
		address = new TextField(45);
		city = new TextField(10);
		state = new TextField();
		zip = new TextField(5);
		addInfo.addActionListener(this);
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(view);
		
		this.add(f);
		this.add(first);
		
		this.add(l);
		this.add(last);
		
		this.add(a);
		this.add(address);
		
		this.add(c);
		this.add(city);
		
		this.add(s);
		this.add(state);
		
		this.add(z);
		this.add(zip);
		
		this.add(addInfo);
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!(first.getText().equals("")) && !(last.getText().equals("")) && !(address.getText().equals("")) && !(city.getText().equals("")) && !(state.getText().equals("")) && !(zip.getText().equals(""))) {
		String data = getLast() + ", " + getFirst() + "\t" + getAddress() + "\t" + getCity() + ", " + getState() + "\t" + getZip();
		System.out.println(getLast() + ", " + getFirst() + "\t" + getAddress() + "\t" + getCity() + ", " + getState() + "\t" + getZip());
		entries.add(new Entry(getFirst(), getLast(),getAddress(), getCity(), getState(), getZip()));
		Collections.sort(entries, compare);
		System.out.println("Vector test:");
		for(Entry obj : entries) {
			obj.print();
		}
		System.out.println("test end");
		try {
			PrintWriter pw = new PrintWriter("AddressBook.txt"); 
			pw = new PrintWriter(new FileWriter("AddressBook.txt",true));
			for(Entry obj : entries) {
				pw.print(obj.returnAddress() + "\n");
			}
			pw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		view.read();
		}
		
	}
}
class addressViewer extends JPanel{
	JTextArea area = new JTextArea();
	String title = "Addresses";
	Border border = BorderFactory.createTitledBorder(title);
	Vector<String> info = new Vector<String>();
	addressViewer(){
		area.setPreferredSize(new Dimension(750,200));
		area.setEditable(false);
		this.setBorder(border);
		this.add(area);
		
		this.read();
	}
	void read() {
		area.setText("");
		try {
			FileReader fr = new FileReader("AddressBook.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null) {
				System.out.println(line);
				info.add(line);
				area.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
class Entry{
	public String first, last, address, city, state, zip;
	Entry(String f, String l, String a, String c, String s,String z){
		first = f;
		last = l;
		address = a;
		city = c;
		state = s;
		zip = z;
	} 
	String returnAddress() {
		return last + ", " + first + "\t" + address + "\t" + city + ", " + state + "\t" + zip;
	}
	void print() {
		System.out.println(last + ", " + first + "\t" + address + "\t" + city + ", " + state + "\t" + zip);

	}
	String getZip() {
		return zip;
	}
	String getState() {
		return state;
	}
	String getCity() {
		return city;
	}
	String getAddress() {
		return address;
	}
	String getLast() {
		return last;
	}
	String getFirst() {
		return first;
	}
	public void sort(final String field, Vector<Entry> item) {
	    Collections.sort(item, new Comparator<Entry>() {
	        @Override
	        public int compare(Entry o1, Entry o2) {
	            if(field.equals("last")) {
	                return o1.last.compareTo(o2.last);
	            } if(field.equals("first")) {
	                return o1.first.compareTo(o2.first);
	            } else if(field.equals("address")) {
	                return o1.address.compareTo(o2.address);
	            }else if(field.equals("city")) {
	                return o1.city.compareTo(o2.city);
	            } else if(field.equals("state")) {
	                return o1.state.compareTo(o2.state);
	            }else if(field.equals("zip")) {
	                return o1.zip.compareTo(o2.zip);
	            }  
	            return 0;
	        }    
	    });
	}
}
class ItemComparator implements Comparator<Entry>{
	public String sortBy;
	ItemComparator(String s){
		sortBy = s;
	}
	@Override
	public int compare(Entry o1, Entry o2) {
		 if(sortBy.equals("last")) {
             return o1.last.compareTo(o2.last);
         } if(sortBy.equals("first")) {
             return o1.first.compareTo(o2.first);
         } else if(sortBy.equals("address")) {
             return o1.address.compareTo(o2.address);
         }else if(sortBy.equals("city")) {
             return o1.city.compareTo(o2.city);
         } else if(sortBy.equals("state")) {
             return o1.state.compareTo(o2.state);
         }else if(sortBy.equals("zip")) {
             return o1.zip.compareTo(o2.zip);
         }  
         return 0;
	}
	
}