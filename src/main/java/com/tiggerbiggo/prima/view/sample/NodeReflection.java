package com.tiggerbiggo.prima.view.sample;

import com.google.gson.Gson;
import com.tiggerbiggo.prima.play.node.core.INode;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

public class NodeReflection {

    private static List<Class<? extends INode>> allImplementedNodes;

    public static List<Class<? extends INode>> getAllImplementedNodes() {
        if (allImplementedNodes == null) {
            setNodeList();
        }
        allImplementedNodes.sort((o1, o2) -> {
            try {
                String s1, s2;
                s1 = o1.newInstance().getName();
                s2 = o2.newInstance().getName();
                return s1.compareTo(s2);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return 0;
        });

        return Collections.unmodifiableList(allImplementedNodes);
    }

    private static void setNodeList() {
        allImplementedNodes = new ArrayList<>();
        Set<Class<? extends INode>> classes = new Reflections(
                "com.tiggerbiggo.prima.play.node.implemented").getSubTypesOf(INode.class);
        System.out.println(classes);
        System.out.println(classes.size());
        classes.iterator().forEachRemaining(aClass -> {
            if (!Modifier.isAbstract(aClass.getModifiers())) {
                if (!aClass.getName().endsWith("BasicRenderNode")) //remove render node from list
                    allImplementedNodes.add(aClass);
            }
        });
    }

    public static INode parseNode(String className, String json) {
        try {
            INode instance = new Gson().fromJson(json, Class.forName(className).asSubclass(INode.class));

            return instance;
        } catch (ClassNotFoundException ex) {
            if (className.contains("prima.primaplay.node"))
                return parseNode(className.replace("prima.primaplay.node", "prima.play.node"), json);


            if (className.contains("node.implemented.output"))
                throw new NodeParseException("Error in parseNode method.", ex);
            else if (className.contains("node.implemented."))
                return parseNode(className.replace("node.implemented.", "node.implemented.output."), json);

            throw new NodeParseException("Error in parseNode method", ex);
        }
    }
}
