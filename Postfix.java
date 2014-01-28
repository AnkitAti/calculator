import javax.swing.JOptionPane;

/**
 * This class calculates the result of the input query.
 * In case of any bad input, this class throws Exception
 * which is handled back in Draw class.
 * @author <a href = "ankmonuati@gmail.com">Ankit</a>
 * @version 1.1.1.0
 * @see result()
 * @see calculateResult()
 */
public class PostFix {
	String input;
	String[] stack = new String[101]; //Acts as stack for post fix expression of input
	int k = 0,j = 0;
	String[] output = new String[200];
	/**
	 * @author Ankit
	 * @version 1.1.1.0
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public String result(String str) throws Exception
	{
		char ch;
		int a = 0,b = 0;
		int start = 1, end;
		input = "(" + str + ")";
		for(int i=0 ; i<input.length(); i++)
		{
			if(input.charAt(i) == '(')
				a++;
			if(input.charAt(i) == ')')
				b++;
		}
		if(a != b)
			throw new Exception("Maths Error");
		for(int i=0; i<input.length(); i++)
		{
			ch = input.charAt(i);
			switch(ch)
			{
			case '+':
				end = i-1;
				if(end == start) //For single digit numbers
					output[j] = Character.toString(input.charAt(start));
				else //For numbers having more than one digit
					output[j] = input.substring(start,end+1);
				j++;
				start = i+1;
				while((see() == 47) || (see() == 42) || (see() == 43)) 
				{
					output[j] = pop();
					j++;
				}
				push("+");
				break;
			case '-':
				end = i-1;
				if(end == start)
						output[j] = Character.toString(input.charAt(start));
				else
					output[j] = input.substring(start,end+1);
				j++;
				start = i+1;
				while((see() == 47) || (see() == 42) || (see() == 43) || (see() == 45))
				{
					output[j] = pop();
					j++;
				}
				push("-");
				break;
			case '/':
				end = i - 1;
				if(end == start)
					output[j] = Character.toString(input.charAt(start));
				else
					output[j] = input.substring(start,end+1);
				j++;
				start = i+1;
				while((see() == 47))
				{
					output[j] = pop();
					j++;
				}
				push("/");
				break;
			case '*':
				end = i - 1;
				if(end == start)
					output[j] = Character.toString(input.charAt(start));
				else
					output[j] = input.substring(start,end+1);
				j++;
				start = i+1;
				while((see() == 47) || (see() == 42))
				{
					output[j] = pop();
					j++;
				}
				push("*");
				break;
			case '(':
				boolean flag = false;
				try
				{
					char cha = input.charAt(i-1);
					for(int i1 = 0; i1 <= 9; i1++)
					{
						if((int)cha == 48+i1)
						{
							flag = true;
							break;
						}
					}
					if(flag)
					{
						end = i-1;
						if(start == end)
							output[j] = Character.toString(input.charAt(start));
						else
							output[j] = input.substring(start,end+1);
						j++;
					}
					if(cha == ')')
						flag = true;
					if(flag)
					{
						flag = false;
						push("*");
					}
				}
				catch(StringIndexOutOfBoundsException e)
				{}
				start = i+1;//Increases the start to point to the next character after '('
				if((input.charAt(i+1) == '-') || (input.charAt(i+1) == '+')) //If the number is negative
					i++;
				push("(");
				break;
			case ')':
				end = i-1;
				if(start == end)
					output[j] = Character.toString(input.charAt(start));
				else
					output[j] = input.substring(start,end+1);
				j++;
				start = i+1;
				while(!(see() == 40))
				{
					output[j] = pop();
					j++;
				}
				pop();
				break;
			}
		}
		return calculateResult();
	}
	public String pop()
	{
		String str;
		try
		{
			k--;
			str = stack[k];
			stack[k] = null;
		}
		catch(StringIndexOutOfBoundsException e)
		{
			str =" ";
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			str = " ";
		}
		return str;
	}
	public void push(String str)
	{
		try
		{
			stack[k] = str;
			k++;
		}
		catch(StringIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null,"Maths Error");
		}
	}
	public int see() //Returns integer since comparison of integer is faster than that of string
	{
		try
		{
			return ((int)stack[k-1].charAt(0));
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null, "Maths Error!");
			return 0;
		}
	}
	/**
	 * @author Ankit
	 * @version 1.1.1.0
	 * @return String
	 * @throws NumberFormatException
	 */
	public String calculateResult()
	{
		double op1,op2,result;
		k = 0;
		for(int count = 0; count<j; count++)
		{
			switch(output[count])
			{
			case "+":
				op1 = Double.parseDouble(pop());
				op2 = Double.parseDouble(pop());
				result = op1 + op2;
				push(Double.toString(result));
				break;
			case "-":
				op1 = Double.parseDouble(pop());
				op2 = Double.parseDouble(pop());
				result = op2 - op1;
				push(Double.toString(result));
				break;
			case "*":
				op1 = Double.parseDouble(pop());
				op2 = Double.parseDouble(pop());
				result = op1 * op2;
				push(Double.toString(result));
				break;
			case "/":
				op1 = Double.parseDouble(pop());
				op2 = Double.parseDouble(pop());
				result = op2/op1;
				push(Double.toString(result));
				break;
			case "":
				break;
			default:
				push(output[count]); //For numbers that are in the output array of string
			}
		}
		return pop();
	}
}
