package Junior_Part.Proiect_P3;

import java.util.*;

public class Proiect_P3 {


    public static void main(String[] args){

        // TODO PROIECT P3.
        String str = "3+(2+1)*2^3^2-8/(5-1*2/2)";
        System.out.println("Crearea formei postfixate");
        System.out.println("Din " + str + " obtinem:");
        System.out.println(shuntingYard(str));
        System.out.println("-----------------------");
        String str1 = "321+232^^*+8512*2/-/-";
        System.out.println("Exaluarea expresiei postfixate");
        System.out.println("Din " + str1 + " obtinem:");
        System.out.println(expEval(str1));


        int [] lista = {1000, 4, 25, 319, 88, 51, 3430, 8471, 701, 1, 2989, 657, 713};
        System.out.println("Sortarea crescatoare a numerelor dupa metoda: SortFix");
        System.out.println(radixSort(lista));
        System.out.println("-----------------------");
        System.out.println("Sortarea descrescatoare a numerelor dupa metoda: SortFix");
        System.out.println(radixSortDescr(lista));

    }

    public static int operationWeight(char c){
        switch (c){
            case '+':
            case '-':
                return 11;
            case '*':
            case '/':
                return 12;
            case '^':
                return 13;
            case '(':
                return 0;
            default:
                System.out.println("This is not an operation symbol in this algorithm");
                return 0;
        }
    }

    public static ArrayList<Character> shuntingYard(String ex){
        Deque<Character> operStack = new LinkedList<>();
        ArrayList<Character> postFix = new ArrayList<>(ex.length());
        char[] expression = ex.toCharArray();
        for (char charact : expression){
            // Digit values - selection
            if (Character.isDigit(charact)){
                postFix.add(charact);
            }
            // Values which are no Digit and no Operation - selection
            else if(charact!= '(' && charact!= ')' && charact!= '*' && charact!= '/' && charact!= '^' &&
                    charact!= '+' && charact!= '-'){
                System.out.println("The given mathematical expression has unknown symbols for this method, please recheck.");
                System.out.println("P.S: The allowed operations are: ')' '(' '+' '-' '*' '/' '^' .");
                return null;
            }
            // Operation values - selection
            else {
                // ( - Case
                if (charact == '('){
                    operStack.push(charact);
                }
                // ) - Case
                else if (charact == ')'){
                    while (operStack.peek()!= null && operStack.peek() != '('){
                        postFix.add(operStack.peek());
                        operStack.pop();
                    }
                    if (operStack.peekFirst()==null){
                        System.out.println("The given mathematical expression is not correct, please check it first.");
                        return null;
                    }
                    operStack.pop();
                }
                // Empty Operation Stack - Case
                else if(operStack.peekFirst() == null){
                    operStack.push(charact);
                }
                // NON-Null Operation Stack - Case
                else{
                    while (operStack.peekFirst()!=null){
                        int o1 = operationWeight(charact);
                        int o2 = operationWeight(operStack.peek());
                        boolean operCheck = ( operStack.peek() != '(') &&
                                (o1 < o2 || (o1 == o2 && operStack.peek() != '^'));
                        if (operCheck){
                            postFix.add(operStack.peek());
                            operStack.pop();
                        }
                        else{
                            operStack.push(charact);
                            break;
                        }
                        if(operStack.peekFirst()==null){
                            operStack.push(charact);
                            break;
                        }
                    }
                }
            }
        }
        while (operStack.peekFirst()!=null){
            postFix.add(operStack.peekFirst());
            operStack.pop();
        }
        return postFix;
    }

    public static String operation(String one, String two, String op){
        char ch = op.charAt(0);
        int rez=0;
        int prim = Integer.parseInt(one);
        int doi = Integer.parseInt(two);
        switch (ch){
            case '+':
                return String.valueOf(doi + prim);
            case '-':
                return String.valueOf(doi - prim);
            case '*':
                return String.valueOf(doi * prim);
            case '/':
                return String.valueOf(doi / prim);
            case '^':
                rez = doi;
                for (int i = 2;i<=prim; i++){
                    rez*=doi;
                }
                return String.valueOf(rez);
            default:
                System.out.println("Simbol necunoscut");
                break;
        }
        return String.valueOf(rez);
    }

    public static boolean isDigit(String str){
        char[] let = str.toCharArray();
        for (int i =0; i<= let.length-1; i++){
            if (!Character.isDigit(let[i])){
                return false;
            }
        }
        return true;
    }

    public static int expEval(String exPostFix){
        char[] exprPostFix = exPostFix.toCharArray();
        Deque<String> stiva = new ArrayDeque<String>();
        Deque<String> operands = new ArrayDeque<String>();
        String op1="";
        String op2="";
        String str="";
        int rez=0;
        for (int i = exprPostFix.length-1; i>=0; i--){
            stiva.push(exprPostFix[i]+"");
        }
        while (stiva.peekFirst()!=null){
            if (isDigit(stiva.peek())) {
                operands.push(stiva.peek());
                stiva.pop();
                continue;
            }
            op1 = operands.peek();
            operands.pop();
            if (operands.peekFirst()!=null){
                op2 = operands.peek();
                operands.pop();
            }
            str = operation(op1,op2,stiva.peek());
            operands.push(str);
            stiva.pop();
        }
        return Integer.parseInt(operands.peek());
     }

    public static int nrDigits(int i){
        int cnt=0;
        while (i!=0){
            cnt++;
            i/=10;
        }
        return cnt;
    }

    public static int theLargest(int[] list){
        int max = Integer.MIN_VALUE;

        for (int i : list){
            if (i>max){
                max =i;
            }
        }
        return nrDigits(max);
    }

    public static String zeroFiller(int is, int mustBe){
        int dif = mustBe - nrDigits(is);
        String str  = Integer.toString(is);
        while (dif!=0){
            str = "0" + str;
            dif--;
        }
        return str;
    }

    public static Deque<Integer> zeroEraser(Deque<String> dez){
        Deque<Integer> ord = new ArrayDeque<>();
        while (dez.peekFirst()!=null){
            ord.offer(Integer.parseInt(dez.peek()));
            dez.pop();
        }
        return ord;
    }

    public static Deque<Integer> zeroEraserDescr(Deque<String> dez){
        Deque<Integer> ord = new ArrayDeque<>();
        while (dez.peekFirst()!=null){
            ord.push(Integer.parseInt(dez.peek()));
            dez.pop();
        }
        return ord;
    }

    public static Deque<Integer> radixSort (int[] list){
        int repNum = theLargest(list);
        Deque<String> listaDezord = new ArrayDeque<>();
        Deque<String> zero = new ArrayDeque<>();
        Deque<String> unu = new ArrayDeque<>();
        Deque<String> doi = new ArrayDeque<>();
        Deque<String> trei = new ArrayDeque<>();
        Deque<String> patru = new ArrayDeque<>();
        Deque<String> cinci = new ArrayDeque<>();
        Deque<String> sase = new ArrayDeque<>();
        Deque<String> sapte = new ArrayDeque<>();
        Deque<String> opt = new ArrayDeque<>();
        Deque<String> noua = new ArrayDeque<>();

        for (int i = 0; i<=list.length-1; i++){
            listaDezord.push(zeroFiller(list[i],repNum));
        }
        repNum--;
        while (repNum>-1){
            while (listaDezord.peekFirst()!=null){
                switch (listaDezord.peek().charAt(repNum)){
                    case '0':
                        zero.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '1':
                        unu.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '2':
                        doi.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '3':
                        trei.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '4':
                        patru.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '5':
                        cinci.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '6':
                        sase.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '7':
                        sapte.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '8':
                        opt.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '9':
                        noua.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                }
            }
            while (zero.peekFirst()!=null){
                listaDezord.offer(zero.peek());
                zero.poll();
            }
            while (unu.peekFirst()!=null){
                listaDezord.offer(unu.peek());
                unu.poll();
            }
            while (doi.peekFirst()!=null){
                listaDezord.offer(doi.peek());
                doi.poll();
            }
            while (trei.peekFirst()!=null){
                listaDezord.offer(trei.peek());
                trei.poll();
            }
            while (patru.peekFirst()!=null){
                listaDezord.offer(patru.peek());
                patru.poll();
            }while (cinci.peekFirst()!=null){
                listaDezord.offer(cinci.peek());
                cinci.poll();
            }
            while (sase.peekFirst()!=null){
                listaDezord.offer(sase.peek());
                sase.poll();
            }
            while (sapte.peekFirst()!=null){
                listaDezord.offer(sapte.peek());
                sapte.poll();
            }
            while (opt.peekFirst()!=null){
                listaDezord.offer(opt.peek());
                opt.poll();
            }
            while (noua.peekFirst()!=null){
                listaDezord.offer(noua.peek());
                noua.poll();
            }
            repNum--;
        }
        Deque<Integer> listaOrdonata = zeroEraser(listaDezord);
        return listaOrdonata;
    }

    public static Deque<Integer> radixSortDescr (int[] list){
        int repNum = theLargest(list);
        Deque<String> listaDezord = new ArrayDeque<>();
        Deque<String> zero = new ArrayDeque<>();
        Deque<String> unu = new ArrayDeque<>();
        Deque<String> doi = new ArrayDeque<>();
        Deque<String> trei = new ArrayDeque<>();
        Deque<String> patru = new ArrayDeque<>();
        Deque<String> cinci = new ArrayDeque<>();
        Deque<String> sase = new ArrayDeque<>();
        Deque<String> sapte = new ArrayDeque<>();
        Deque<String> opt = new ArrayDeque<>();
        Deque<String> noua = new ArrayDeque<>();

        for (int i = 0; i<=list.length-1; i++){
            listaDezord.push(zeroFiller(list[i],repNum));
        }
        repNum--;
        while (repNum>-1){
            while (listaDezord.peekFirst()!=null){
                switch (listaDezord.peek().charAt(repNum)){
                    case '0':
                        zero.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '1':
                        unu.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '2':
                        doi.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '3':
                        trei.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '4':
                        patru.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '5':
                        cinci.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '6':
                        sase.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '7':
                        sapte.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '8':
                        opt.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                    case '9':
                        noua.offer(listaDezord.peek());
                        listaDezord.pop();
                        break;
                }
            }
            while (zero.peekFirst()!=null){
                listaDezord.offer(zero.peek());
                zero.poll();
            }
            while (unu.peekFirst()!=null){
                listaDezord.offer(unu.peek());
                unu.poll();
            }
            while (doi.peekFirst()!=null){
                listaDezord.offer(doi.peek());
                doi.poll();
            }
            while (trei.peekFirst()!=null){
                listaDezord.offer(trei.peek());
                trei.poll();
            }
            while (patru.peekFirst()!=null){
                listaDezord.offer(patru.peek());
                patru.poll();
            }while (cinci.peekFirst()!=null){
                listaDezord.offer(cinci.peek());
                cinci.poll();
            }
            while (sase.peekFirst()!=null){
                listaDezord.offer(sase.peek());
                sase.poll();
            }
            while (sapte.peekFirst()!=null){
                listaDezord.offer(sapte.peek());
                sapte.poll();
            }
            while (opt.peekFirst()!=null){
                listaDezord.offer(opt.peek());
                opt.poll();
            }
            while (noua.peekFirst()!=null){
                listaDezord.offer(noua.peek());
                noua.poll();
            }
            repNum--;
        }
        Deque<Integer> listaOrdonata = zeroEraserDescr(listaDezord);
        return listaOrdonata;
    }
}
