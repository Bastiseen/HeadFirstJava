package bastiseen.training;

public class PredictionStatusTest
{

	public static void main(String[] args)
	{
		PredictionStatus status = PredictionStatus.NORMAL;
		
		System.out.println(status.name());
		status = PredictionStatus.valueOf("ERROR");
		System.out.println(status.name());
	}

}
