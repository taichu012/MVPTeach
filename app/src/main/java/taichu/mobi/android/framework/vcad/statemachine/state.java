package taichu.mobi.android.framework.vcad.statemachine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 状态树是一个多叉分层的tree结构，每个节点只有一个父father，但有多个子son
 */
public class State {
    private static String STATE_DEFAULT = "<EMPTY>";
    private static String STATE_BEGIN = "<BEGIN>";
    private static String STATE_END = "<END>";
    private String state;
    private State fatherState;
    private HashMap<String, State> sonStateMap;

    //constructor
    public State(State fatherState, HashMap<String, State> sonStateMap, String state) {
        this.fatherState = fatherState;
        this.sonStateMap = sonStateMap;
        this.state = state;
    }

    public State(String state) {
        this.state = state;
    }

    public State() {
        this.fatherState = new State();
        this.sonStateMap = new HashMap<String, State>();
        this.state = STATE_DEFAULT;
    }

    /**
     * 链式添加son（双向绑定），返回son
     *
     * @param son
     * @return
     */
    public State son(State son) {
        this.sonStateMap.put(son.getState(), son);
        son.setFatherState(this);
        return son;
    }

    /**
     * 链式添加son（双向绑定），返回自己（父）
     *
     * @param son
     * @return
     */
    public State putSon(State son) {
        this.sonStateMap.put(son.getState(), son);
        son.setFatherState(this);
        return this;
    }

    /**
     * 添加头状态节点head，并挂接第一个状态节点之前；
     * 这会替换传入节点的父节点为head节点；
     *
     * @param son
     * @return
     */
    public static State begin(State son) {
        State headState = new State(STATE_BEGIN);
        son.setFatherState(headState);
        return headState.son(son);
    }

    /**
     * 添加末尾状态节点tail，并挂接到最后一个状态节点之后；
     * 这会删除当前状态节点的所有儿子节点（如果有的话），并留下唯一的tail节点作为儿子节点；
     *
     * @return
     */
    public State end() {
        State tailState = new State(STATE_END);
        tailState.setFatherState(this);
        this.clearSon();
        return this.putSon(tailState);
    }

    /**
     * remove all son of current state node!
     */
    public void clearSon() {
        this.sonStateMap.clear();
    }

    /**
     * 打印以当前状态节点为父的所有子孙树
     *
     * @return
     */
    public String toString(State state) {
        HashMap<String, State> sonStateMap;
        if (state.getSonStateMap() == null || state.getSonStateMap().isEmpty()) {
            //当前状态节点没有子孙，打印并返回；
            System.out.println("{<" + state.getState() + ", null>}");
            //return printOneStateWithoutSon(state);
            return "";
        } else {
            //当前状态节点有1个或多个子孙，用广度遍历一层一层从父亲到子孙遍历
            sonStateMap = state.getSonStateMap();
            Iterator iter = sonStateMap.entrySet().iterator();
            Map.Entry<String, State> entry;
            System.out.println("{<" + state.getState() + ", (");//打印多儿子节点的前缀
            while (iter.hasNext()) {
                entry = (Map.Entry<String, State>) iter.next();
                if (entry.getKey() == STATE_END) {
                    //如果第一个（仅有的一个）是tail，则表明找到尾巴tail，打印并返回；
                    System.out.println(STATE_END + ">}");
                    //return printOneStateWithTail(state);
                    return "";
                } else {
                    //多个儿子的情况处理！打印当前节点，并递归儿子节点，完成后返回；
                    System.out.println(entry.getValue().getState() + ", ");
                }
            }
            System.out.println(", null)");//打印多儿子节点的后缀
            //广度遍历，所以先打印，后再次遍历；
            while (iter.hasNext()) {
                entry = (Map.Entry<String, State>) iter.next();
                this.toString(entry.getValue());
            }
            return "";
        }
    }


    /**
     * 打印1个状态节点
     * @param state
     * @return
     */
    public String printOneStateWithoutSon(State state){
        return "<"+state.getState()+", null>\n";
    }
    public String printOneStateWithTail(State state){
        return "<"+state.getState()+", "+STATE_END+">\n";
    }


    //gettor/settor

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public State getFatherState() {
        return fatherState;
    }

    public void setFatherState(State fatherState) {
        this.fatherState = fatherState;
    }

    public HashMap<String, State> getSonStateMap() {
        return sonStateMap;
    }

    public void setSonStateMap(HashMap<String, State> sonStateMap) {
        this.sonStateMap = sonStateMap;
    }

    //main
    public void main(String[] args) {
        State stInit = new State("ST_INIT");
        State stLogining = new State("ST_LOGINING");
        State stLoginOk = new State("ST_LOGIN_OK");
        State stLoginNok = new State("ST_LOGIN_NOK");
        //方式1：建立状态转移链
        begin(stInit).son(stLogining).son(stLoginOk);
        begin(stInit).son(stLogining).son(stLoginNok);
        stLoginOk.son(stInit).end();
        stLoginNok.son(stInit).end();
        //方式2：建立状态转移链
        stInit.son(stLogining).putSon(stLoginOk).putSon(stLoginNok);
        stLoginOk.son(stInit).end();
        stLoginNok.son(stInit).end();

        stInit.getFatherState().toString(this);


    }
}
