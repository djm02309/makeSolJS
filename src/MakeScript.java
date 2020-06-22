import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class MakeScript {
    public static void main(String[] args) {
        MakeScript ms = new MakeScript();
        ms.make();
    }


    private void make() {
        File path = new File("/home/ether/Downloads/solidity코드/page1/");  //경로바꾸기
        final String pattern = ".bin";
        String[] fileList = path.list((dir, name) -> {
            return name.endsWith(pattern); // pattern 형식으로 끝나는 거(.bin으로 끝나는거)
        });
        assert fileList != null;
        if (fileList.length > 0) {
            for (String s : fileList) { // 모든 파일에 대해
                int pos = s.lastIndexOf(".");
                String fileName = s.substring(0, pos);  //파일이름
                String varName = makeVarName(fileName); //변수이름
                String abiFileName = fileName + ".abi";  //abi파일 이름
                // bin파일 객체 생성
//				File binFile = new File("경로" + fileList[i]); //경로바꾸기
                // 입력 스트림 생성
                Path path1 = Paths.get("/home/ether/Downloads/solidity코드/page1/" + s);  //경로바꾸기
                // abi파일 객체 생성
//				File abiFile = new File("경로" + abiFileName); //경로바꾸기
                // 입력 스트림 생성
                Path path2 = Paths.get("/home/ether/Downloads/solidity코드/page1/" + abiFileName);  //경로바꾸기
                // 캐릭터셋 지정
                Charset cs = StandardCharsets.UTF_8;
                // 파일 내용담을 리스트
                List<String> listBin = new ArrayList<>();  //Bin파일 내용 담겨있음
                List<String> listAbi = new ArrayList<>();  //Abi파일 내용 담겨있음
                try {
                    listBin = Files.readAllLines(path1, cs);
                    listAbi = Files.readAllLines(path2, cs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StringBuilder contentAbi = new StringBuilder();
                StringBuilder contentBin = new StringBuilder();
                for (String readLine : listBin) {//bin파일 내용 읽기
                    contentBin.append(readLine);
                }
                for (String readLine : listAbi) { //abi파일 내용 읽기
                    contentAbi.append(readLine);
                }
                //파일에 쓰기 앞부분에 생성자
                String intoFile = "var " + varName + "Interface = web3.eth.contract(" + contentAbi + ");"
                        + "var " + varName + "Tx = " + varName + "Interface.new({from:eth.accounts[0],data:" + contentBin + ",gas:3000000},function(e,contract){console.log(e,contract);if(typeof contract.address !=='undefined'){console.log('Contract minded! address:'+contract.address+'transactionHash: '+contract.transactionHash);}})";     //여기에 내용 채우기
//		    출처: https://jeong-pro.tistory.com/69 [기본기를 쌓는 정아마추어 코딩블로그]
                System.out.println(s);

                File newJsS = new File("경로 채우기" + fileName + ".js"); //경로 채우기
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(newJsS));
                    bw.write(intoFile);  //쓰기 부분 채우기
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 인자로 들어온 bin abi파일을 읽어온다
    // 그걸 바탕으로 var ___ = 내용 내용 이런 스크립트를 짠다
    // 출력으로 만들어준다?

    private String makeVarName(String argu) {
        String shortName = argu.substring(2, 4);// 2글자 뽑기
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < shortName.length(); i++) {
            switch (shortName.charAt(i)) {
                case '0':
                    result.append("Zero");
                    break;
                case '1':
                    result.append("One");
                    break;
                case '2':
                    result.append("Two");
                    break;
                case '3':
                    result.append("Three");
                    break;
                case '4':
                    result.append("Four");
                    break;
                case '5':
                    result.append("Five");
                    break;
                case '6':
                    result.append("Six");
                    break;
                case '7':
                    result.append("Seven");
                    break;
                case '8':
                    result.append("Eight");
                    break;
                case '9':
                    result.append("Nine");
                    break;
                case 'a':
                case 'A':
                    result.append("A");
                    break;
                case 'b':
                case 'B':
                    result.append("B");
                    break;
                case 'c':
                case 'C':
                    result.append("C");
                    break;
                case 'd':
                case 'D':
                    result.append("D");
                    break;
                case 'e':
                case 'E':
                    result.append("E");
                    break;
                case 'f':
                case 'F':
                    result.append("F");
                    break;
            }
        }
        return result.toString();
    }
}