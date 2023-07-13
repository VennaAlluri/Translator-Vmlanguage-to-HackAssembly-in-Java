import java.util.regex.Pattern;
public class ML {
      public String writeCode(String x){
            String value=x;
            String label_value="";
            String[] val;
            if(Pattern.compile("\\d").matcher(x).find()){
                  if(x.contains(":")){
                        value=x.split(":")[1];
                        x=x.split(":")[0];
                  }
            }
            else if(x.contains(":")){
                  value=x.split(":")[1];
                  x=x.split(":")[0];
            }
            x=x.replaceAll("\\d","");
            String cmd;
            switch (x){
                  case "value_assign" :
                        val=value.split(",");
                        cmd = "@" + val[0] + "\n"+val[1]+"\n";
                        return cmd;
                  case "to_memory":
                        val=value.split(",");
                        cmd="@"+val[0]+"\nM="+val[1]+"\n";
                        return cmd;
                  case "to_pointer":
                        cmd="@"+value+"\nA=M\nM=D\n";
                        return cmd;
                  case "from_pointer" :
                        cmd="@"+value+"\nA=M\nD=M\n";
                        return cmd;
                  case "incr" :
                        val=value.split(",");
                        cmd="@"+val[1]+"\nD=A\n@"+val[0]+"\nM=M+D\n";
                        return cmd;
                  case "decr" :
                        val=value.split(",");
                        cmd="@"+val[1]+"\nD=A\n@"+val[0]+"\nM=M-D\n";
                        return cmd;
                  case "pnt_assign" :
                       val=value.split(",");
                       cmd="@"+val[0]+"\nA=M\nD=M\n@"+val[1]+"\nA=M\nM=D\n";
                       return cmd;
                  case "add_const" :
                        val=value.split(",");
                        if(val[0].charAt(0)=='-'){
                              val[0]=val[0].replaceAll("-","");
                              cmd="@"+val[0]+"\nD=-A\n@"+val[1]+"\nD=D+A\n";
                        }
                        else {
                              cmd = "@" + val[0] + "\nD=A\n@" + val[1] + "\nD=D+A\n";
                        }

                        return cmd;
                  case "sub_const" :
                       val=value.split(",");
                        if(val[0].charAt(0)=='-'){
                             val[0]=val[0].replaceAll("-","");
                              cmd="@"+val[0]+"\nD=-A\n@"+val[1]+"\nD=D-A\n";

                        }
                        else {
                              cmd = "@" + val[0] + "\nD=A\n@" + val[1] + "\nD=D-A\n";
                        }
                        return cmd;
                  case "neg" :
                        if(value.charAt(0)=='-'){
                              value=value.replaceAll("-","");
                              cmd="@"+value+"\nD=A\n";
                        }
                        else {
                        cmd="@"+value+"\nD=-A\n"; }
                        return cmd;
                  case "and" :
                        val=value.split(",");
                        if(val[0].charAt(0)=='-'){
                              val[0]=val[0].replaceAll("-","");
                              cmd="@"+val[0]+"\nD=-A\n@"+val[1]+"\nD=D&A\n";
                        }
                        else {
                        cmd="@"+val[0]+"\nD=A\n@"+val[1]+"\nD=D&A\n"; }
                        return cmd;
                  case "or" :
                        val=value.split(",");
                        if(val[0].charAt(0)=='-'){
                              val[0]=val[0].replaceAll("-","");
                              cmd="@"+val[0]+"\nD=-A\n@"+val[1]+"\nD=D|A\n";
                        }
                        else {
                        cmd="@"+val[0]+"\nD=A\n@"+val[1]+"\nD=D|A\n"; }
                        return cmd;
                  case "not" :
                        if(value.charAt(0)=='-'){
                              value=value.replaceAll("-","");
                              cmd="@"+value+"\nD=-A\n"+"\nD=!D\n";
                        }
                        else {
                        cmd="@"+value+"\nD=!A\n"; }
                        return cmd;
                  case "label":
                        cmd="("+value+")\n" ;
                        return cmd;
                  case "jump" :
                        val=value.split(",");
                        label_value=val[0];
                        cmd="@"+label_value+"\n"+val[1]+"\n";
                        return cmd;
                  case "add" :
                        val=value.split(",");
                        cmd="@"+val[1]+"\nD=A\n"+"\n@"+val[0]+"\nM=M-D\nA=M\nD=M\nA=A+1\nD=D+M\nA=A-1\nM=D\n";
                        return cmd;
                  case "sub" :
                        val=value.split(",");
                        cmd="@"+val[1]+"\nD=A\n"+"\n@"+val[0]+"\nM=M-D\nA=M\nD=M\nA=A+1\nD=D-M\nA=A-1\nM=D\n";
                        return cmd;
                  case "addm":
                        val=value.split(",");
                        cmd = "@" + val[0] + "\nD=M\n@" + val[1] + "\nD=D+A\n";
                        return cmd;

                  default:
                        return "";
            }
      }
   }
