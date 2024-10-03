import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.sql.*;
public class DBConn {
        String acno="",dacno="",name="",fname="",mname="",phno="";
        int ob,option;
        char choice;
        public void input()throws IOException {

            do{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter A/c Number");
                acno = br.readLine();
                System.out.println("Enter Your Name");
                name = br.readLine();
                System.out.println("Enter Father's Name");
                fname = br.readLine();
                System.out.println("Enter Mother's Name");
                mname = br.readLine();
                System.out.println("Enter Phone Number");
                phno = br.readLine();
                System.out.println("Enter Your Opening Balance");
                ob = Integer.parseInt(br.readLine());
                write();

                System.out.println("Do You Wand To Open Another Account Press Y for Yes And N for No");
                choice = (char) br.read();
            }
            while (choice=='y'||choice=='Y');
            userchoice();
        }
        public void write() throws IOException {
            String url = "jdbc:mysql://localhost:3306/accounts";
            String username = "root";
            String password = "2466";
            String sql = "select country from country where country_id = 44";
            String sql1 = "insert into previous_records values('2','bhanu','satish','soniya',88665439,'1100000')";
            try {
            Connection con = DriverManager.getConnection(url,username,password);
            Statement st = con.createStatement();
            st.executeUpdate("insert into previous_records values('"+acno+"','"+name+"','"+fname+"','"+mname+"','"+phno+"','"+ob+"')");
            con.close();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }

        public void userchoice()throws IOException{
            System.out.println("Enter Your Choice");
            System.out.println("Press 1 For Account Opening"+"\n"+"Press 2 For Amount Depositing"+"\n"+"Press 3 For Amount withdrawn"+"\n"+"Press 4 for Check Balance");
            BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
            option=Integer.parseInt(br.readLine());
            switch (option){
                case 1:
                    input();
                    break;
                case 2:
                    validate('d');
                    break;
                case 3:
                    validate('w');
                    break;
                case 4:
                    //validate('c');
                    check();
                    break;
            }
        }

        public void validate(char ch) throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Account Number");
            dacno= br.readLine();
            File f = new File(dacno);
            if (f.exists()){
                switch(ch)
                {
                    case 'd':
                        deposit();
                        break;
                    case 'w':
                        withdrawn();
                        break;
                    case 'c':
                        check();
                        break;
                }
            }
            else
            {
                System.out.println("Account not exists");
            }
        }

        public void deposit() throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Amount To Deposit");
            int amount=Integer.parseInt(br.readLine());
            if (amount>50000){
                System.out.println("Enter Your Pancard Number");
                String pn = br.readLine();
                FileOutputStream op = new FileOutputStream("CustomerData", true);
                DataOutputStream dop = new DataOutputStream(op);
                dop.writeChars("Customer Pan Number:"+pn+"\n\n");
            }
            FileInputStream fis = new FileInputStream(dacno);
            DataInputStream dis = new DataInputStream(fis);
            int amt=dis.readInt();
            dis.close();
            fis.close();
            amount=amount+amt;
            FileOutputStream op = new FileOutputStream(dacno);
            DataOutputStream dop = new DataOutputStream(op);
            dop.writeInt(amount);
            System.out.println("Total Avail Balance:"+" "+amount);
            userchoice();
        }

        public void withdrawn() throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Withdrawn Amount");
            int amount=Integer.parseInt(br.readLine());
            FileInputStream fis = new FileInputStream(dacno);
            DataInputStream dis = new DataInputStream(fis);
            int amt=dis.readInt();
            dis.close();
            fis.close();
            amount=amt-amount;
            if (amount>=5000){
                FileOutputStream op = new FileOutputStream(dacno);
                DataOutputStream dop = new DataOutputStream(op);
                dop.writeInt(amount);
                System.out.println("Total Avail Balance:" +amount);
            }
            else {
                System.out.println("Can't Withdrawn Hold Minimum Balance");
            }


            userchoice();
        }

        public void check() throws IOException {
            String url = "jdbc:mysql://localhost:3306/accounts";
            String username = "root";
            String password = "2466";


            try {
                Connection con = DriverManager.getConnection(url,username,password);
                Statement st = con.createStatement();
                String sql = "select * from previous_records";
                ResultSet rs =st.executeQuery(sql);
                while(rs.next()) {
                    String dbacno = rs.getString(1);
                    String dbname = rs.getString(2);
                    String dbfathername = rs.getString(3);
                    String dbmothername = rs.getString(4);
                    int dbphone  = rs.getInt(5);
                    int dbopeningbal= rs.getInt(6);
                    System.out.print("Account No "+"\n"+dbacno+"\n"+"Customer Name"+dbname+"\n"+"Customer Fathers Name"+dbfathername);
                }
            }

            catch (Exception ex){
                System.out.println(ex);
            }
            userchoice();
        }

        public static void main(String args[]) throws IOException {
            DBConn db = new DBConn();
//            db.userchoice();
            db.check();
        }
    }
