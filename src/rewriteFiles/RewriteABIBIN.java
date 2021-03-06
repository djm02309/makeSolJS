package rewriteFiles;

import java.io.*;

public class RewriteABIBIN {
    public static void main(String[] args){
        RewriteABIBIN re = new RewriteABIBIN();
        File dir = new File("/home/ether/Downloads/solidity코드/");
        File[] fileList = dir.listFiles();  //pages디렉토리들

        try{
            for (int i = 0; i < 1; i++) {  //1개만 테스트 해보는 용도. 아래 줄이 진짜
//            for (int i = 0; i < fileList.length; i++) {
                File file = fileList[i]; //page 디렉토리 하나

                File[] binList = FilenameFilterUtils.getfilenameFilterList( file.getPath() , ".bin" ); //bin파일 리스트
                File[] abiList = FilenameFilterUtils.getfilenameFilterList( file.getPath() , ".abi" ); //abi파일 리스
                //[출처] java(자바) FilenameFilter를 이용한 특정 확장자 파일만 가져오기|작성자 세바니

                int j = 0;
                for (File solFile : binList) {
                    String contractName="";
                    if(solFile.length()!=0) {  //파일 크기가 0이 아니면
                        contractName = re.rewriteBin(solFile);
                    }
                    if(abiList[j].length()!=0) { //파일 크기가 0이 아니면
                        re.rewriteAbi(contractName, abiList[j]);
                    }
                }
            }
        }catch(IOException e){
        }
    }
    public String rewriteBin(File file){
        try{
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            String bin = "", name="",result="";
            while((line = bufReader.readLine()) != null){
                if(line.contains("home")){
                    name = line.substring(line.indexOf(":"), line.length()-8);
                }
                if(line.startsWith("6080")){  //safeMath 아닐때? safeMath가 6080
                    bin =line; //bin
                    result = name;
                    break;
                }
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            //파일에 쓰기

            bufReader.close();
            FileWriter fw = new FileWriter(file);  //주소바꾸기
            fw.write(bin);
            fw.close();
            return result;
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
        //출처: https://jeong-pro.tistory.com/69 [기본기를 쌓는 정아마추어 코딩블로그]


    //bin파일에서 6080만 찾아서 저장하기트
        return "";
    }
    public void rewriteAbi(String name, File file){
        try{
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            String abi = "";
            while((line = bufReader.readLine()) != null){
                if(line.contains(name)){
                    abi = bufReader.readLine();
                    break;
                }
            }
            //파일에 쓰기
            FileWriter fw = new FileWriter(file);  //주소바꾸기
            fw.write(abi);
            fw.close();
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();
        }catch (FileNotFoundException e) {
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
