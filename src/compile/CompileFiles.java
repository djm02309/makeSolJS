package compile;

import rewriteFiles.FilenameFilterUtils;

import java.io.*;

public class CompileFiles {
    public static void main(String[] args){
        CompileFiles cp = new CompileFiles();
        File dir = new File("/home/ether/Downloads/solidity코드/");
        //String Directory = "/home/ether/Downloads/solidity코드/page";
        File[] fileList = dir.listFiles();  //pages디렉토리들

        try{
            for (int i = 0; i < fileList.length; i++) {
                File file = fileList[i]; //page 디렉토리 하나
                File[] txts = FilenameFilterUtils.getfilenameFilterList( file.getPath() , ".txt" );  //page_에 디렉토리 내에 파일들
                for (int k = 0; k < txts.length; k++) {
                    File sol = txts[k];
                    if (sol.isFile()) {// 파일이 있다면 파일 이름 출력
                        if(cp.checkVersion(sol)){
                            cp.compile(sol.getName(),i+1, sol);
                            System.out.println("\t 파일 이름 = " + sol.getName());
                        }

                    }
                }
            }
        }catch(IOException e){
        }
        //출처: https://ra2kstar.tistory.com/133 [초보개발자 이야기.]
    }
    public void compile(String name, int pageNum, File file) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("./solc", "--bin", "/home/ether/solcs/solidity_0.5.16/build/solc/", file.getPath());
        builder.directory(new File("/home/ether/solcs/solidity_0.5.16/build/solc/"));
        Process p = builder.start();


        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

//        출처: https://wildcoots.tistory.com/entry/자바프로그램으로-외부-프로그램-실행-하기 [하늘을 닮고싶은 늑대...]
    }
    public boolean checkVersion(File file) {
        try{
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            String bin = "", name="";
            while((line = bufReader.readLine()) != null){
                if(line.contains("pragma")){
                    String a =line.substring(line.indexOf(".")+1, line.indexOf(".")+2);  ///이부분 수정
                    if(line.substring(line.indexOf("."), line.indexOf(".")+2) =="5" ){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            //파일에 쓰기

            bufReader.close();

        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
        return false;

    }
}
