package com.example.main;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class Node {
    Node next = null;
    String type, problem, answer0, answer1, answer2, answer3, correct, Ch;
    int no;

    Node(int no, String problem, String answer0, String answer1, String answer2, String answer3, String correct, String Ch) {
        this.problem = problem;
        this.answer0 = answer0;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correct = correct;
        this.Ch = Ch;
    }

    Node(Node next, int no, String problem, String answer0, String answer1, String answer2, String answer3, String correct, String Ch) {
        this.next = next;
        this.problem = problem;
        this.answer0 = answer0;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correct = correct;
        this.Ch = Ch;
    }

    Node(Node data) {
        this.problem = data.problem;
        this.answer0 = data.answer0;
        this.answer1 = data.answer1;
        this.answer2 = data.answer2;
        this.answer3 = data.answer3;
        this.correct = data.correct;
        this.Ch = data.Ch;
    }

    Node(Node data, Node next) {
        this.problem = data.problem;
        this.answer0 = data.answer0;
        this.answer1 = data.answer1;
        this.answer2 = data.answer2;
        this.answer3 = data.answer3;
        this.correct = data.correct;
        this.next = next;
        this.Ch = data.Ch;
    }
}

//링크드 리스트 구현
class LinkedList {
    Node front = null;
    Node rear = null;
    int nodeCount = 0;

    // 링크드 리스트에 노드 삽입
    void add(Node data) {
        nodeCount++;
        if (front == null) {
            front = new Node(data);
            rear = front;
        } else {
            rear.next = new Node(data);
            rear = rear.next;
        }
    }

    // 리스트 뒤에 노드 삽입
    void insertNode(Node data) {
        nodeCount++;
        if (front == null) {
            front = new Node(data);
            rear = front;
        } else {
            rear.next = new Node(data);
            rear = rear.next;
        }
    }

    // 리스트 중간에 노드 삽입
    void insertNode(Node data, Node node) {
        if (node == null) {
            System.out.println("Node is null");
            return;
        }
        nodeCount++;
        if (front == null) {
            front = new Node(data);
            rear = front;
        } else {
            node.next = new Node(data, node.next);
        }
    }

    // 리스트의 앞에 노드 삽입
    void insertFirstNode(Node data) {
        nodeCount++;
        if (front == null) {
            front = new Node(data);
            rear = front;
        } else {
            front = new Node(data, front);
        }
    }

    // 맨 앞 노드 반환
    Node getFirstNode() {
        return front;
    }

    // 맨 뒤 노드에서 값 반환하고 제거
    Node pop() {
        if (front == null) {
            System.out.println("list is Empty");
            return null;
        }
        nodeCount--;
        Node data = front;
        front = front.next;
        return data;
    }

    //리스트 순서 랜덤하게 변경
    LinkedList randomMixList() {
        LinkedList tmp = new LinkedList();
        Node mainList_node = front.next;
        Node insert_point;
        tmp.add(front);
        while (mainList_node != null) {
            int insert_point_index = (int) (Math.random() * (tmp.nodeCount + 1));
            insert_point = tmp.front;
            if (insert_point_index == 0) {
                tmp.insertFirstNode(mainList_node);
            } else {
                insert_point_index -= 1;
                for (int i = 0; i < insert_point_index; i++) {
                    insert_point = insert_point.next;
                }
                tmp.insertNode(mainList_node, insert_point);
            }
            mainList_node = mainList_node.next;
        }
        return tmp;
    }
    public Boolean nextNotNull(){
        if(front.next != null){
            return true;
        }
        return false;
    }

    public void goNext(){
        if(nextNotNull()){
            front = front.next;
        }
    }

    public int getNodeCount(){
        return nodeCount;
    }

    public void delCount(int num){
        while (nodeCount>num){
            nodeCount--;
            front=front.next;
        }
    }
}


public class GetProblem {
    LinkedList list;

    GetProblem() {
        list = new LinkedList();
    }

    public void insertProblem(InputStream txtResource) {
        String result = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        try {
            len = txtResource.read();
            // 반복문을 수행하면서 파일 내용을 읽음
            while (len != -1) {
                byteArrayOutputStream.write(len);
                len = txtResource.read();
            }
            // 저장된 값을 변수에 저장 실시
            result = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            txtResource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line[] = result.split("\n");
        String line0[] = line[0].split("<<");
        String line1[];

        for (int i = 1; i < line.length; i++) {
            line1 = line[i].split("<<");
            list.add(new Node(Integer.parseInt(line1[0]),line1[1], line1[2],line1[3],line1[4],line1[5],line1[6],line0[0]));
        }
    }

    public void insertMissProblem(Context con){
        String str = readFromFile(con);
        String line[] = str.split("\n");
        String line0[] = line[0].split("<<");
        String line1[];
        for (int i = 1; i < line.length; i++) {
            line1 = line[i].split("<<");
            list.add(new Node(Integer.parseInt(line1[0]),line1[1], line1[2],line1[3],line1[4],line1[5],line1[6],line0[0]));
        }
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("ErrProblem.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        }
        return ret;
    }
    public LinkedList getRandomList(){
        return list.randomMixList();
    }
}
