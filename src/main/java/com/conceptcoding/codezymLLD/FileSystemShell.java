package com.conceptcoding.codezymLLD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class FileSystemShell {

    private static class Node{
        String name;
        Node parent;
        TreeMap<String,Node> children;

        Node(String name, Node parent){
            this.name = name;
            this.children = new TreeMap<>();
            this.parent = parent;
        }

    }

    Node root;
    Node cwd;

    public FileSystemShell(){
        root = new Node("",null);
        root.parent = root;
        cwd = root;
    }

    public String pwd(){
        //present working directory
        if(cwd == root){
            return "/";
        }

        List<String> parts = new ArrayList<>();
        Node cur = cwd;

        while(cur!=root){
            parts.add(cur.name);
            cur=cur.parent;
        }
        Collections.reverse(parts);

        StringBuilder ans = new StringBuilder();

        for(String p:parts){
            ans.append("/").append(p);
        }
        return ans.toString();
    }
    private List<String> split(String path){

        List<String> res = new ArrayList<>();
        StringBuilder cur = new StringBuilder();

        for(char c:path.toCharArray()){
            if(c=='/'){
                if(cur.length()>0){
                    res.add(cur.toString());
                    cur.setLength(0);
                }
            }else{
                cur.append(c);
            }
        }
        if(cur.length()>0){
            res.add(cur.toString());
        }
        return res;
    }
    public void mkdir(String path){
        Node cur = (path.charAt(0)=='/')?root:cwd;

        List<String> parts = split(path);

        for(String seg:parts){
            if(seg.equals(".")){
                continue;
            }

            if(seg.equals("..")){
                cur = cur.parent;
                continue;
            }

            if(!cur.children.containsKey(seg)){
                cur.children.put(seg, new Node(seg, cur));
            }
            cur = cur.children.get(seg);
        }
    }

    public void cd(String path){
        Node cur = (path.charAt(0)=='/')?root:cwd;

        List<String> parts = split(path);

        for(String seg:parts){
            if(seg.equals(".")){
                continue;
            }

            if(seg.equals("..")){
                cur = cur.parent;
                continue;
            }

            if(seg.equals("*")){
                if(!cur.children.isEmpty()){
                    cur = cur.children.firstEntry().getValue();
                }else{
                    continue;
                }
            }else{
                if(!cur.children.containsKey(seg)){
                    return;
                }else{
                    cur = cur.children.get(seg);
                }
            }
        }
        cwd  = cur;
    }
}
/*
Operation	Complexity
mkdir	O(pathSegments × log children)
cd	O(pathSegments × log children)
pwd	O(depth)
 */