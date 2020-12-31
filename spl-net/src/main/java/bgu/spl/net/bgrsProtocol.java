package bgu.spl.net;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.ConnectionHandler;

public class bgrsProtocol implements MessagingProtocol<String> {
    private String user=null;       //seted at login
    private Database database= Database.getInstance();

    @Override
    public String process(String msg) {
        String opcode = msg.substring(0,2);
        String info = msg.substring(2,msg.length());
        switch(opcode){
            case "01": {  //Admin register
                int index=info.indexOf('\0');
                String username=info.substring(0,index);
                info = msg.substring(index+1,msg.length());
                index=info.indexOf('\0');
                String password=info.substring(0,index);
                if(database.isRegistered(username))
                    break; // needs to send eror
                else {
                    database.adminRegister(username, password);
                    // send ACK
                }
            }

            case "02":{  //student register
                int index=info.indexOf('\0');
                String username=info.substring(0,index);
                info = msg.substring(index+1,msg.length());
                index=info.indexOf('\0');
                String password=info.substring(0,index);
                if(database.isRegistered(username))
                    break; // needs to send eror
                else {
                    database.studentRegister(username, password);
                    // send ACK
                }
            }

            case "03":{ //login
                int index=info.indexOf('\0');
                String username=info.substring(0,index);
                info = msg.substring(index+1,msg.length());
                index=info.indexOf('\0');
                String password=info.substring(0,index);
                if(user==null && database.loginApproved(username,password)) {
                    user = username;
                    database.loginStudent(username);
                }
                else {
                //send error (dont know how at this point)
                //(which error for wrong password, which error for already login
                }
            }
            case "04":{ // logout
                if(user==null)
                   break; //eror
                else{
                    database.logoutStudent(user);
                    user=null;
                    //send ack , the client receives this ack in order to terminate
                }

            }
            case "05":{ // register to course

            }
            case "06":{ // check kdam course

            }
            case "07":{

            }
            case "08":{

            }
            case "09":{

            }
            case "10":{

            }
            case "11":{

            }

        }
        return null;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
