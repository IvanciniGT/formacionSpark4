public class CalcularPI {

    // Por ejemplo, en una mñaquina como la mía, que tiene 8 cores: 12,5% de uso de CPU
    // Se usa 1 solo core... porque mi código solo usa 1 Thread
    public static void main(String[] args){
        final int totalDeDardos = 1000 * 1000 * 1000;
        double estimacionDePI = calcularPI(totalDeDardos);

        System.out.println("PI es aproximadamente " + estimacionDePI);
    }


    public static double calcularPI(int numeroTotalDeDardos){
        int numeroDardosDentro = 0;
        for(int numeroDeDardo = 0; numeroDeDardo < numeroTotalDeDardos; numeroDeDardo++){
            double x = Math.random();
            double y = Math.random();
            double distanciaAlCentro = Math.sqrt(x*x + y*y);
            if(distanciaAlCentro <= 1) numeroDardosDentro++;
        }
        return 4. * numeroDardosDentro / numeroTotalDeDardos;

    }
}
