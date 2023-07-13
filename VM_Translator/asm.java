import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class asm {
     ArrayList<String> asm=new ArrayList<>();
    private ArrayList<String> segment=new ArrayList<>();
     ArrayList<String> all=new ArrayList<>();
    private ML ml=new ML();
public void push_cmd(String inst){
    StringBuffer cmd = new StringBuffer(inst.replaceAll("\\s","")).delete(0,4);
    String variable=cmd.toString().replaceAll("\\d","");
    String value=inst.replaceAll("\\D","");
    String assembly_code;


      switch (variable){
      case "constant" :
          all.add(value);
          if(!segment.contains("SP")){
              assembly_code=ml.writeCode("value_assign:"+"256,D=A")+ml.writeCode("to_memory:SP,D");
              asm.add(assembly_code);
              segment.add("SP");
          }
            assembly_code=ml.writeCode("value_assign:"+value+",D=A")+ml.writeCode("to_pointer:SP")+ml.writeCode("incr:SP,1");
            asm.add(assembly_code);
            break;

        case "local":
        assembly_code=ml.writeCode("addm:LCL,"+value)+ml.writeCode("to_memory:addr,D")+ml.writeCode("pnt_assign:addr,SP")+ml.writeCode("incr:SP,1");
        asm.add(assembly_code);
        break;

         case "argument" :
         assembly_code=ml.writeCode("addm:ARG,"+value)+ml.writeCode("to_memory:arg,D")+ml.writeCode("pnt_assign:arg,SP")+ml.writeCode("incr:SP,1");
         asm.add(assembly_code);
         break;

         case "this" :
         assembly_code=ml.writeCode("addm:THIS,"+value)+ml.writeCode("to_memory:this,D")+ml.writeCode("pnt_assign:this,SP")+ml.writeCode("incr:SP,1");
         asm.add(assembly_code);
         break;

         case "that" :
         assembly_code=ml.writeCode("addm:THAT,"+value)+ml.writeCode("to_memory:that,D")+ml.writeCode("pnt_assign:that,SP")+ml.writeCode("incr:SP,1");
         asm.add(assembly_code);
         break;

         case "temp" :
        assembly_code=ml.writeCode("add_const:5,"+value)+ml.writeCode("to_memory:temp,D")+ml.writeCode("pnt_assign:temp,SP")+ml.writeCode("incr:SP,1");
        asm.add(assembly_code);
        break;
        case "static" :
        assembly_code=ml.writeCode("add_const:16,"+value)+ml.writeCode("to_memory:stat,D")+ml.writeCode("pnt_assign:stat,SP")+ml.writeCode("incr:SP,1");
         asm.add(assembly_code); 
         break;
            case "pointer" :
                if (value.equals("0")){
                    assembly_code=ml.writeCode("value_assign:THIS,D=M")+ml.writeCode("to_pointer:SP")+ml.writeCode("incr:SP,1");
                    asm.add(assembly_code);
                    break;
                }
                else{
                    assembly_code=ml.writeCode("value_assign:THAT,D=M")+ml.writeCode("to_pointer:SP")+ml.writeCode("incr:SP,1");
                    asm.add(assembly_code);
                    break;
                }

    }
    }
    public void pop_cmd(String inst){
        StringBuffer cmd = new StringBuffer(inst.replaceAll("\\s","")).delete(0,3);
        String variable=cmd.toString().replaceAll("\\d","");
        String value=inst.replaceAll("\\D","");
        String assembly_code;
        switch (variable){
            case "local" :
                assembly_code=ml.writeCode("addm:LCL,"+value)+ml.writeCode("to_memory:addr,D")+ml.writeCode("decr:SP,1")+ml.writeCode("pnt_assign:SP,addr");
                asm.add(assembly_code);
                break;
            case "argument" :
                assembly_code=ml.writeCode("addm:ARG,"+value)+ml.writeCode("to_memory:arg,D")+ml.writeCode("decr:SP,1")+ml.writeCode("pnt_assign:SP,arg");
                asm.add(assembly_code);
                break;
            case "this" :
                assembly_code=ml.writeCode("addm:THIS,"+value)+ml.writeCode("to_memory:this,D")+ml.writeCode("decr:SP,1")+ml.writeCode("pnt_assign:SP,this");
                asm.add(assembly_code);
                break;
            case "that" :
                assembly_code=ml.writeCode("addm:THAT,"+value)+ml.writeCode("to_memory:that,D")+ml.writeCode("decr:SP,1")+ml.writeCode("pnt_assign:SP,that");
                asm.add(assembly_code);
                break;
            case "static" :
                assembly_code=ml.writeCode("add_const:16,"+value)+ml.writeCode("to_memory:stat,D")+ml.writeCode("decr:SP,1")+ml.writeCode("pnt_assign:SP,stat");
                asm.add(assembly_code);
                break;
            case "temp" :
                assembly_code=ml.writeCode("add_const:5,"+value)+ml.writeCode("to_memory:temp,D")+ml.writeCode("decr:SP,1")+ml.writeCode("pnt_assign:SP,temp");
                asm.add(assembly_code);
                break;
            case "pointer" :
                if(value.equals("0")){
                    assembly_code=ml.writeCode("decr:SP,1")+ml.writeCode("from_pointer:SP")+ml.writeCode("to_memory:THIS,D");//+ml.writeCode("incr:SP,1");
                    asm.add(assembly_code);
                    break;
                }
                else{
                    assembly_code=ml.writeCode("decr:SP,1")+ml.writeCode("from_pointer:SP")+ml.writeCode("to_memory:THAT,D");//+ml.writeCode("incr:SP,1");
                    asm.add(assembly_code);
                    break;
                }

        }
    }
    public void operation(String inst){
    String optr=inst.replaceAll("\\s","");
    String assembly_code;
    switch (optr){
        case "add" :
            assembly_code=ml.writeCode("add:SP,2")+ml.writeCode("to_pointer:SP")+ ml.writeCode("incr:SP,1");
        asm.add(assembly_code);
        break;

        case "sub" :
            assembly_code=ml.writeCode("sub:SP,2")+ml.writeCode("to_pointer:SP")+ ml.writeCode("incr:SP,1");
            asm.add(assembly_code);
            break;

        case "neg" :
            assembly_code=ml.writeCode("decr:SP,1")+ml.writeCode("neg:"+all.get(all.size()-1))+ml.writeCode("to_pointer:SP")+ml.writeCode("incr:SP,1");
            asm.add(assembly_code);
            break;

        case "and" :
            assembly_code=ml.writeCode("decr:SP,2")+ml.writeCode("and:"+all.get(all.size()-2)+","+all.get(all.size()-1))+ml.writeCode("to_pointer:SP")
                    + ml.writeCode("incr:SP,1");
            asm.add(assembly_code);
            break;

        case "or" :
            assembly_code=ml.writeCode("decr:SP,2")+ml.writeCode("or:"+all.get(all.size()-2)+","+all.get(all.size()-1))+ml.writeCode("to_pointer:SP")
                    + ml.writeCode("incr:SP,1");
            asm.add(assembly_code);
            break;

        case "eq" :
            assembly_code=ml.writeCode("sub:SP,2")+ml.writeCode("decr:SP,2")+ml.writeCode("to_memory:SP,0")+
                    ml.writeCode("jump:EQ,D;JEQ")+ ml.writeCode("jump:A,0;JMP")+ml.writeCode("label:EQ")
                    +ml.writeCode("to_memory:SP,-1")+ml.writeCode("incr:SP,1")+ml.writeCode("label:A");
            asm.add(assembly_code);
            if (all.get(all.size()-2).equals(all.get(all.size()-1))){
                all.set(all.size()-2,"-1");
            }
            else{
                all.set(all.size()-2,"0");
            }
            break;

        case "gt" :
            assembly_code=ml.writeCode("sub:SP,2")+ml.writeCode("decr:SP,2")+ml.writeCode("to_memory:SP,0")+
                    ml.writeCode("jump:GT,D;JGT")+ ml.writeCode("jump:B,0;JMP")+ml.writeCode("label:GT")
                    +ml.writeCode("to_memory:SP,-1")+ml.writeCode("incr:SP,1")+ml.writeCode("label:B");
            asm.add(assembly_code);
            if (Integer.parseInt(all.get(all.size()-2))>Integer.parseInt(all.get(all.size()-1))){
                all.set(all.size()-2,"-1");
            }
            else{
                all.set(all.size()-2,"0");
            }
            break;

        case "lt" :
            assembly_code=ml.writeCode("sub_const:"+all.get(all.size()-2)+","+all.get(all.size()-1))+ml.writeCode("decr:SP,2")+ml.writeCode("to_memory:SP,0")+
                    ml.writeCode("jump:LT,D;JLT")+ ml.writeCode("jump:C,0;JMP")+ml.writeCode("label:LT")
                    +ml.writeCode("to_memory:SP,-1")+ml.writeCode("incr:SP,1")+ml.writeCode("label:C");
            asm.add(assembly_code);
            if (Integer.parseInt(all.get(all.size()-2))<Integer.parseInt(all.get(all.size()-1))){
                all.set(all.size()-2,"-1");
            }
            else{
                all.set(all.size()-2,"0");
            }
            break;


    }
    }

    public void read_file (String path) throws IOException {
        FileReader read=new FileReader(path);
        BufferedReader bufferedReader=new BufferedReader(read);
        String line="";
        StringBuffer B;
        String[] opt={"add","sub","neg","eq","gt","lt","and","or","not"};
        while((line=bufferedReader.readLine())!=null){
            if(line.length()!=0 && line.charAt(0)!='/'){
                B=new StringBuffer(line);

                if (line.contains("//")) {
                    B.delete(line.indexOf('/'), line.length());
                }
                if(line.contains("push")){
                    push_cmd(B.toString());

                }
                else if(line.contains("pop")){
                    pop_cmd(B.toString());

                }
                else if(Arrays.asList(opt).contains(B.toString())){
                    operation(B.toString());

                }

            }
        }
        read.close();
        bufferedReader.close();
    }
    public void write_file(String path) throws Exception {
    File file =new File(path);
    if(file.exists()){
        throw new Exception("!This file is already exist");
    }
    else{
        FileWriter write = new FileWriter(file);
        BufferedWriter buffer_write=new BufferedWriter(write);
        for (String s:asm){
            buffer_write.write(s+"\n");
        }
        buffer_write.close();
    }
    }
    public static void main(String... args) throws Exception {
      asm obj=new asm();
     obj.read_file("C:\\Users\\venna\\OneDrive\\Documents\\nand2tetris\\nand2tetris\\tools\\Main.vm");
     obj.write_file("C:\\Users\\venna\\OneDrive\\Documents\\nand2tetris\\nand2tetris\\tools\\Main.asm");
    }
}