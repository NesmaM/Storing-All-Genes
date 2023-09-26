
/**

 * @author (Nesma Abouzaid) 
 * @version (6/30/2023)
 */
import edu.duke.*;

import java.io.File;

public class Part1 {
     public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while(currIndex != -1) {
            int diff = currIndex - startIndex;
            if(diff % 3 == 0) {
                return currIndex;
            } else {
                currIndex = dna.indexOf(stopCodon, currIndex + 1);
            }
        }

        return -1;
    }

   public String findGene(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if(startIndex == -1 || where == -1) {
            return "";
        }

        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");

        int minIndex = 0;
        
        if(taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex)) {
            minIndex = tgaIndex;
        } else {
            minIndex = taaIndex;
        }

        if(minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;
        }

        if(minIndex == -1) {
            return "";
        }
        
        return dna.substring(startIndex, minIndex + 3);
    }

    public StorageResource getAllGenes(String dna){
        //create an empty StorageResource, call it geneList
        StorageResource geneList = new StorageResource();
        //Set startIndex to 0
        int startIndex = 0;
        //Repeat the following steps
        while (true){
            //Find the next gene after startIndex
            String currentGene = findGene(dna, startIndex);
            //If no gene was found, leave this loop
            if (currentGene.isEmpty()) {
                break;
            }
            //Add that gene to the geneList
            geneList.add(currentGene);
            //Set startIndex to just past the end of the geneList
            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
        }
        //Your answer is geneList
        return geneList;
        }

    
    public double cgRatio (String dna){
        
        double cgValue =0.0;
        double strLen = dna.length();
        
        for(int i =0; i<strLen ; i++){
            if (dna.charAt(i) == 'C' || dna.charAt(i) == 'G'){
                cgValue ++;
            }
        }
        
        double ratio = cgValue/strLen;
             
        return ratio;
    }    
    
    public int countCTG (String dna){
        int numberOfCTG =0;
        int startIndex =0;
        int index = dna.indexOf("CTG", startIndex);
        while(true){
            if(index == -1 || numberOfCTG>dna.length()){
                break;
            }
            
            numberOfCTG ++;
            index = dna.indexOf("CTG", index+3);
            
        }
        return numberOfCTG;
        
    }
    
    public void processGenes(StorageResource sr){
        int count = 0;
        int counting = 0;
        System.out.println("Printing genes with +9 charas:");
        for (String gene9 : sr.data()){
            if (gene9.length() > 9){
                System.out.println(gene9);
            }
        }
        for (String plus9 : sr.data()){
          if (plus9.length() > 9){
              count = count +1;
            }
        }
        System.out.println("Printing number of strings above: " + count);
        for (String cgRat : sr.data()){
           cgRatio(cgRat);
           if (cgRatio(cgRat) > 0.35)
           System.out.println("Gene with C-G ratio higher than 0.35 = " + cgRat + " and the ratio is: "+ cgRatio(cgRat));
        }
        
        for (String longest : sr.data()){
            if (longest.length() > counting){
                counting = longest.length();
            }
        }
        System.out.println("Length of the longest gene= " + counting);
        
    }
    
    public void howManyGenes() {
        int startIndex = 0;
        int count = 0;
        int countGene = 0;
        int counting = 0;
    
        FileResource fr = new FileResource("Rch38dnapart.fa.txt");
        String dna = fr.asString().toUpperCase();

        

        while (true) {
            String gene = findGene(dna, startIndex);
            countGene ++;
            if (gene == "") {
                break;
            }

            startIndex = dna.indexOf(gene, startIndex) + gene.length();
            

            if(gene.length() > 60) {
                count++;
            }
        }
                System.out.println("Number of genes are: " + countGene);
        System.out.println("How many genes are: " + count);

}
    
    public void testOn(String dna){
        System.out.println("Testing getAllGenes on ");
        StorageResource genes = getAllGenes(dna);
        for (String g: genes.data()){
            System.out.println("This is the list of genes: "+ g);
        }
        
    }
    public void test(){
        testOn("ATGATCTAATTTATGCTGCAACGGTGAAGA");
    }
    
    public void testRatio(){
        System.out.println("The CG ration in dna is: " +cgRatio("ATGCTATAA"));
    }
    
    public void testCTG(){
        System.out.println("The number of ocuurence of CTG is: "+countCTG("ATGCCATAG"));
        System.out.println("The number of ocuurence of CTG is: "+countCTG("ATGCCATAGCTGAATGCCCTG"));
    }
    
    public void testProcessGenes(){
        processGenes(CreationofSR());
    
    }
    
    public StorageResource CreationofSR(){
        StorageResource sr = new StorageResource();
        sr.add("ATGCCCCGGTAA");
        sr.add("ATGCCCGGGTTTTTTTTTTTTTTTTAA");
        sr.add("ATGTTTTTTTTTTTTTTTTAA");
        for(String s : sr.data()){
            System.out.println("this is my list of genes: " + s);
        }
        return sr;
    }
    
    public StorageResource srIsFile(){
        FileResource fr = new FileResource("brca1line.fa");
        String Newdna = fr.asString();
        StorageResource sr = new StorageResource();
        sr.add(Newdna);
        for(String s : sr.data()){
            System.out.println("this is my list of genes: " + s);
        }
        return sr;
    }
    
    public void testBara(){
        howManyGenes();
    }

    }
  


