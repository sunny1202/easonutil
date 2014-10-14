package idv.eason.test;

import java.io.*;
import java.util.ArrayList;

public class SparseMatrix {

    private static int[][] A = null,B = null ;
    
    public SparseMatrix(){
        super();
        //定義A、B矩陣
        /*
        A = new int[][]{{0,6,0,0,0,0},
                        {7,8,0,0,0,12},
                        {0,0,0,-8,0,0},
                        {0,4,0,0,0,0},
                        {0,0,0,18,0,0},
                        {0,0,7,0,0,0}};
        B = new int[][]{{0,0,0,8,0,0},
                        {0,1,0,0,5,0},
                        {6,0,0,8,0,0},
                        {0,0,7,0,0,2},
                        {0,0,0,3,0,0},
                        {0,4,0,0,0,0}};
        */               
        
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        SparseMatrix sm = new SparseMatrix();
        int indexA=1,indexB=1;//3-tuple指標 從1開始0為記錄此矩陣原為N*M
        ArrayList matrixC = new ArrayList();
        try {
            System.out.println("稀疏矩陣 A：");
            BufferedReader BR = new BufferedReader(new FileReader("./Array_A.txt"));
            A = sm.fileReader(BR);
            int[][] _newA = sm.make3tuple(A);
            System.out.println("稀疏矩陣 B：");
            BR = new BufferedReader(new FileReader("./Array_B.txt"));
            A = sm.fileReader(BR);
            int[][] _newB = sm.make3tuple(A);
            int[][] sumC = null ;
            int[] sumRow = null ;
            //其指標不可大於等於其矩陣大小
            while(indexA < _newA.length && indexB < _newB.length){
                //比較該指標矩陣值的大小
                if(_newA[indexA][0] < _newB[indexB][0]){
                    matrixC.add(new int[]{_newA[indexA][0],_newA[indexA][1],_newA[indexA][2]});
                    indexA++;
                }else if(_newA[indexA][0] == _newB[indexB][0]){
                    if(_newA[indexA][1] < _newB[indexB][1]){
                        matrixC.add(new int[]{_newA[indexA][0],_newA[indexA][1],_newA[indexA][2]});
                        indexA++;
                    }else if(_newA[indexA][1] == _newB[indexB][1]){
                        //若合計為0不加入矩陣，但指標要加1
                        if(_newA[indexA][2]+_newB[indexB][2] != 0){
                            matrixC.add(new int[]{_newA[indexA][0],_newA[indexA][1],_newA[indexA][2]+_newB[indexB][2]});
                        }
                        indexA++;
                        indexB++;
                    }else{
                        matrixC.add(new int[]{_newB[indexB][0],_newB[indexB][1],_newB[indexB][2]});
                        indexB++;
                    }
                }else{
                    matrixC.add(new int[]{_newB[indexB][0],_newB[indexB][1],_newB[indexB][2]});
                    indexB++;
                }
            }
            //判斷指標是否已等於矩陣大小，若否則將該矩陣加入新矩陣中
            while(indexA < _newA.length){
                matrixC.add(new int[]{_newA[indexA][0],_newA[indexA][1],_newA[indexA][2]});
                indexA++;
            }
            while(indexB < _newB.length){
                matrixC.add(new int[]{_newB[indexB][0],_newB[indexB][1],_newB[indexB][2]});
                indexB++;
            }
            System.out.println("矩陣A+B的和：");
            sumC = new int[matrixC.size()][3];
            for(int i = 0 ; i < matrixC.size() ; i++){
                sumRow = (int[])matrixC.get(i);
                sumC[i][0] = sumRow[0];
                sumC[i][1] = sumRow[1];
                sumC[i][2] = sumRow[2];
                System.out.println(sumC[i][0]+" "+sumC[i][1]+" "+sumC[i][2]);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 傳入矩陣將矩陣轉成3-tuple矩陣
     * @param array
     * @return
     */
    public int[][] make3tuple(int[][] array){
        int[][] _3tuple = null ;
        int[] rowlist = null;
        ArrayList al = new ArrayList();
        for(int i=0 ; i < array.length ; i++){
            for(int j = 0 ; j < array[i].length ; j++){
                if(array[i][j] != 0){
                    rowlist = new int[3];
                    rowlist[0] = i+1;
                    rowlist[1] = j+1;
                    rowlist[2] = array[i][j];
                    al.add(rowlist);
                }
            }
        }
        _3tuple = new int[al.size()+1][3];
        //將array
        _3tuple[0][0] = array.length;
        _3tuple[0][1] = array[0].length;
        _3tuple[0][2] = al.size();
        System.out.println(_3tuple[0][0]+" "+_3tuple[0][1]+" "+_3tuple[0][2]);
        for(int i=0 ; i < al.size() ; i++){
            rowlist = (int[])al.get(i);
            _3tuple[i+1][0] = rowlist[0];
            _3tuple[i+1][1] = rowlist[1];
            _3tuple[i+1][2] = rowlist[2];
            System.out.println(_3tuple[i+1][0]+" "+_3tuple[i+1][1]+" "+_3tuple[i+1][2]);
        }
        return _3tuple ;
    }
    
    public int[][] fileReader(BufferedReader fe) throws IOException{
        String lineText = "";
        int[][] array = null ;
        String[] subArray = null ;
        ArrayList al = new ArrayList();
        int row = -1 ;
        while((lineText = fe.readLine()) != null){
            if(lineText.trim().length() > 0){
                subArray = lineText.split(",");
                al.add(subArray);
            }
        }
        array = new int[al.size()][subArray.length];
        for(int j = 0 ; j < al.size() ; j++){
            subArray = (String[])al.get(j);
            for(int x = 0 ; x < subArray.length ; x++){
                array[j][x] = Integer.parseInt(subArray[x]);
            }
        }
        return array;
        
    }

}
