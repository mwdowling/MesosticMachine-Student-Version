package mesosticSystem;


/**
 * @author Martin Dowling 

 * 
 * NOTE October 2017.  This is dumb.  Just collate two files into a collated file.
 * Then collate the collated file with a third file.  This can be done in a nested way
 * if the collating file returns a file address.  
 *    
 * This object features a nested collection of if/else statements 
 * which moves line by line through the three target files:
 * 
 * line1 is the line from the AllMesostics file 
 * line2 is the line from the ChapterSounds file 
 * line3 is the line from the ChapterPlaces file
 * 
 * The smallest of the three is found and written to allMesosticsCollated file 
 * and the line of the file with the smallest is advanced 
 * for the next iteration of the while statement.
 * 
 * Try blocks surround the statements which advance a line
 * 
 * Catch blocks accompany these try blocks to catch a NullPointerException 
 * which occurs where there are no further lines to advance in a file.
 * 
 * Catch blocks continue process with code comparing the two remaining lines,  
 * finding and writing the smaller of the two to allMesosticsCollated file
 * 
 * Additional try/catch blocks are nested within these catch blocks to
 * catch the NullPointerException which occurs where there are no
 * further lines to advance in one of the two remaining files.
 * 
 * Nested catch blocks write all the remaining lines of the last
 * remaining file to the collated file.
 * 
 * The structure of this method is:
 * 
 * OUTER IF line1<line2
 * 
 * 		INNER IF (line1<line2)<line3 
 * 			-->write line 1 
 * 			-->try (advance line 1) catch (compare line2 and line3)
 * 
 * 		INNER ELSE IF Line1<Line3<Line2 
 * 			-->write line 1 
 * 			-->try (advance line 1) catch (compare line2 and line3)
 * 
 * 		INNER FINAL ELSE line3<(line1<line2) 
 * 			-->write line 3 
 * 			-->try (advance line 3) catch (compare line1 and line2)
 * 
 * OUTER ELSE IF line2<line3 
 * 		-->write line 2 
 * 		-->try (advance line 2) catch (compare line1 and line3)
 * 
 * OUTER FINAL ELSE line3 smallest 
 * 		-->write line 3 
 * 		-->try (advance line3) catch (compare line1 and line2)
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MesosticFinisherCollated implements ImesosticMaker {

	// input variables for files to collate according to index values of lines
	private String allMesosticsFileAddress;
	private String chapterSoundsFileAddress;
	private String chapterPlacesFileAddress;

	// output variable
	private String allMesosticsCollatedFileAddress;

	//constructor with input variables
	public MesosticFinisherCollated(String allMesosticsFileAddress, String chapterSoundsFileAddress,
			String chapterPlacesFileAddress, String allMesosticsCollatedFileAddress) {

		this.allMesosticsFileAddress = allMesosticsFileAddress;
		this.chapterSoundsFileAddress = chapterSoundsFileAddress;
		this.chapterPlacesFileAddress = chapterPlacesFileAddress;
		this.allMesosticsCollatedFileAddress = allMesosticsCollatedFileAddress;
	}
	
	/*
	 * (non-Javadoc)
	 * @see mesosticSystem.ImesosticMaker#runMaker()
	 */
	@Override
	public void runMaker() throws IOException, InterruptedException {
		// file readers for each target file
		BufferedReader br1 = new BufferedReader(new FileReader(new File(allMesosticsFileAddress)));
		String line1 = br1.readLine();// first line of first mesostic
		String[] line1Array = line1.split("\t");
		Integer line1Index = new Integer(line1Array[0]);

		BufferedReader br2 = new BufferedReader(new FileReader(new File(chapterSoundsFileAddress)));
		String line2 = br2.readLine();
		String[] line2Array = line2.split("\t");
		Integer line2Index = new Integer(line2Array[0]);

		BufferedReader br3 = new BufferedReader(new FileReader(new File(chapterPlacesFileAddress)));
		String line3 = br3.readLine();
		String[] line3Array = line3.split("\t");
		Integer line3Index = new Integer(line3Array[0]);

		while (line1 != null && line2 != null && line3 != null) {

			// OUTER IF CLAUSE
			if (line1Index < line2Index) {
				// INNER IF CLAUSE WHERE line1<line2
				if (line2Index < line3Index) {

					// line1Index is smallest; write line1 to
					// allMesosticsCollated file
					System.out.println(line1);
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsCollatedFileAddress), true));
					bw.write(line1);
					bw.newLine();
					bw.close();

					// advance line1 to next line and reformat
					try {

						line1 = br1.readLine();
						line1Array = line1.split("\t");
						line1Index = new Integer(line1Array[0]);

					} catch (NullPointerException e) {

						/*
						 * if no more of line1, continue with two line
						 * comparison of line2 with line3.
						 * 
						 * Note:
						 * 
						 * This exception should never occur because the index
						 * of the last line of the AllMesostics file should be
						 * set at 10000
						 */

						while (line2 != null && line3 != null) {

							if (line2Index < line3Index) {

								/*
								 * line2Index is smallest; write line2 to
								 * MesosticsCollated
								 */

								System.out.println(line2);
								BufferedWriter bw2 = new BufferedWriter(
										new FileWriter(new File(allMesosticsCollatedFileAddress), true));
								bw2.write(line2);
								bw2.newLine();
								bw2.close();

								try {

									// advance and reformat line2
									line2 = br1.readLine();
									line2Array = line2.split("\t");
									line2Index = new Integer(line2Array[0]);

								} catch (NullPointerException e1) {

									/*
									 * if no more line1 AND line2 write line3 to
									 * MesosticCollated to finish
									 */
									if (line3 != null) {
										System.out.println(line3);
										BufferedWriter bw3 = new BufferedWriter(
												new FileWriter(new File(allMesosticsCollatedFileAddress), true));
										bw3.write(line3);
										bw3.newLine();
										bw3.close();
									}

								}

							} else {

								/*
								 * line3Index is smallest; write line3 to
								 * MesosticsCollated
								 */

								System.out.println(line3);
								BufferedWriter bw2 = new BufferedWriter(
										new FileWriter(new File(allMesosticsCollatedFileAddress), true));
								bw2.write(line3);
								bw2.newLine();
								bw2.close();

								// advance line3 to next line and reformat
								try {

									line3 = br3.readLine();
									line3Array = line3.split("\t");
									line3Index = new Integer(line3Array[0]);

								} catch (NullPointerException e1) {

									/*
									 * if no more of line3 AND line1 write line2
									 * to finish
									 */

									if (line2 != null) {

										System.out.println(line2);
										BufferedWriter bw1 = new BufferedWriter(
												new FileWriter(new File(allMesosticsCollatedFileAddress), true));
										bw1.write(line2);
										bw1.newLine();
										bw1.close();
									}
								} // end of try/catch writing line2 to finish
							} // end of else clause writing line3
						} // end of while loop comparing line2 and line3
					} // end of try/catch writing line1

					// END OF INNER IF CLAUSE
					// INNER ELSE IF CLAUSE WHERE Line1<Line2 BUT Line3<Line2
				} else if (line1Index < line3Index) {

					// line1Index is smallest; write line1 to MesosticsCollated
					System.out.println(line1);
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsCollatedFileAddress), true));
					bw.write(line1);
					bw.newLine();
					bw.close();

					// advance line1 to next line and reformat
					try {

						line1 = br1.readLine();
						line1Array = line1.split("\t");
						line1Index = new Integer(line1Array[0]);

					} catch (NullPointerException e) {

						/*
						 * if no more of line1, continue with two line
						 * comparison of line2 with line3
						 * 
						 * Note: This exception should never occur because the
						 * index of the last line of the AllMesostics file is
						 * set at 10000
						 */
						while (line2 != null && line3 != null) {

							if (line2Index < line3Index) {

								/*
								 * line2Index is smallest; write line2 to
								 * MesosticsCollated
								 */

								System.out.println(line2);
								BufferedWriter bw2 = new BufferedWriter(
										new FileWriter(new File(allMesosticsCollatedFileAddress), true));
								bw2.write(line2);
								bw2.newLine();
								bw2.close();

								try {

									// advance and reformat line2
									line2 = br1.readLine();
									line2Array = line2.split("\t");
									line2Index = new Integer(line2Array[0]);

								} catch (NumberFormatException e1) {

									/*
									 * if no more line1 AND line2 write line3 to
									 * MesosticCollated to finish
									 */
									if (line3 != null) {
										System.out.println(line3);
										BufferedWriter bw3 = new BufferedWriter(
												new FileWriter(new File(allMesosticsCollatedFileAddress), true));
										bw3.write(line3);
										bw3.newLine();
										bw3.close();
									}

								}

							} else {

								/*
								 * line3Index is smallest; write line3 to
								 * MesosticsCollated
								 */

								System.out.println(line3);
								BufferedWriter bw2 = new BufferedWriter(
										new FileWriter(new File(allMesosticsCollatedFileAddress), true));
								bw2.write(line3);
								bw2.newLine();
								bw2.close();

								// advance line3 to next line and reformat
								try {

									line3 = br3.readLine();
									line3Array = line3.split("\t");
									line3Index = new Integer(line3Array[0]);

								} catch (NullPointerException e1) {

									/*
									 * if no more of line3 AND line1 write line2
									 * to finish
									 */

									if (line2 != null) {

										System.out.println(line2);
										BufferedWriter bw1 = new BufferedWriter(
												new FileWriter(new File(allMesosticsCollatedFileAddress), true));
										bw1.write(line2);
										bw1.newLine();
										bw1.close();
									}
								} // end of try/catch writing line2 to finish
							} // end of else clause writing line3
						} // end of while loop comparing line2 and line3
					} // end of try/catch writing line1

					// END OF INNER ELSE-IF CLAUSE
					// INNER FINAL ELSE CLAUSE WHERE line3<(line1<line2)
				} else {

					// line3Index is smallest; write line3 to MesosticsCollated
					System.out.println(line3);
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsCollatedFileAddress), true));
					bw.write(line3);
					bw.newLine();
					bw.close();

					// advance and reformat line3
					try {
						line3 = br3.readLine();
						line3Array = line3.split("\t");
						line3Index = new Integer(line3Array[0]);

					} catch (NullPointerException e) {

						/*
						 * if no more of line3, continue with two line
						 * comparison of line1 with line2
						 * 
						 */

						while (line1 != null && line2 != null) {

							if (line1Index < line2Index) {

								/*
								 * line1Index is smallest; write line1 to
								 * MesosticsCollated
								 */

								System.out.println(line1);
								BufferedWriter bw2 = new BufferedWriter(
										new FileWriter(new File(allMesosticsCollatedFileAddress), true));
								bw2.write(line1);
								bw2.newLine();
								bw2.close();

								try {

									// advance and reformat line1
									line1 = br1.readLine();
									line1Array = line1.split("\t");
									line1Index = new Integer(line1Array[0]);

								} catch (NumberFormatException e1) {

									/*
									 * If no more line1 AND line3 write line2 to
									 * MesosticCollated to finish
									 * 
									 * Note: This exception should never occur
									 * because the index of the last line of the
									 * mesostics file is set at 10000
									 */

									if (line2 != null) {
										System.out.println(line2);
										BufferedWriter bw3 = new BufferedWriter(
												new FileWriter(new File(allMesosticsCollatedFileAddress), true));
										bw3.write(line2);
										bw3.newLine();
										bw3.close();
									}

								}

							} else {

								/*
								 * line2Index is smallest; write line2 to
								 * MesosticsCollated
								 */

								System.out.println(line2);
								BufferedWriter bw2 = new BufferedWriter(
										new FileWriter(new File(allMesosticsCollatedFileAddress), true));
								bw2.write(line2);
								bw2.newLine();
								bw2.close();

								// advance line2 to next line and reformat
								try {

									line2 = br3.readLine();
									line2Array = line2.split("\t");
									line2Index = new Integer(line2Array[0]);

								} catch (NullPointerException e1) {

									/*
									 * if no more of line2 write line1 to finish
									 * 
									 */

									while (line1 != null) {

										System.out.println(line1);
										BufferedWriter bw1 = new BufferedWriter(
												new FileWriter(new File(allMesosticsCollatedFileAddress), true));
										bw1.write(line1);
										bw1.newLine();
										bw1.close();
									}
								} // end of try catch finishing with line1
							} // end of else clause writing line2
						} // end of while loop comparing line1 and line2
					} // end of try/catch writing line3
				} // END OF INNER ELSE CLAUSE writing line3

				// OUTER ELSE IF CLAUSE WHERE LINE1 IS NOT SMALLEST AND IS
				// EXCLUDED
			} else if (line2Index < line3Index) {

				// line2Index is smallest; write line2 to MesosticsCollated
				System.out.println(line2);
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsCollatedFileAddress), true));
				bw.write(line2);
				bw.newLine();
				bw.close();

				// advance line2 to next line and reformat
				try {

					line2 = br2.readLine();
					line2Array = line2.split("\t");
					line2Index = new Integer(line2Array[0]);

				} catch (NullPointerException e) {

					/*
					 * if no more of line2 continue with two-line comparison of
					 * line1 with line3
					 */
					while (line1 != null && line3 != null) {

						if (line1Index < line3Index) {

							/*
							 * line1Index is smallest; write line1 to
							 * MesosticsCollated
							 */
							System.out.println(line1);
							BufferedWriter bw1 = new BufferedWriter(
									new FileWriter(new File(allMesosticsCollatedFileAddress), true));
							bw1.write(line1);
							bw1.newLine();
							bw1.close();

							try {

								// advance and reformat line1
								line1 = br1.readLine();
								line1Array = line1.split("\t");
								line1Index = new Integer(line1Array[0]);

							} catch (NumberFormatException e1) {

								/*
								 * if no more line1 and line2 print the rest of
								 * line3
								 * 
								 * Note: This exception should never occur
								 * because the index of the last line of the
								 * mesostics file is set at 10000
								 */
								if (line3 != null) {
									System.out.println(line3);
									BufferedWriter bw2 = new BufferedWriter(
											new FileWriter(new File(allMesosticsCollatedFileAddress), true));
									bw2.write(line3);
									bw2.newLine();
									bw2.close();
								}
							}

						} else {

							/*
							 * line3Index is smallest; write line3 to
							 * MesosticsCollated
							 */
							System.out.println(line3);
							BufferedWriter bw2 = new BufferedWriter(
									new FileWriter(new File(allMesosticsCollatedFileAddress), true));
							bw2.write(line3);
							bw2.newLine();
							bw2.close();

							// advance line3 to next line and reformat
							try {

								line3 = br3.readLine();
								line3Array = line3.split("\t");
								line3Index = new Integer(line2Array[0]);

							} catch (NullPointerException e1) {

								/*
								 * if no more of line3 and line2 write line1 to
								 * finish
								 */
								if (line1 != null) {
									System.out.println(line1);
									BufferedWriter bw3 = new BufferedWriter(
											new FileWriter(new File(allMesosticsCollatedFileAddress), true));
									bw3.write(line1);
									bw3.newLine();
									bw3.close();
								}
							} // end of try/catch writing line1
						} // end of else clause writing line3
					} // end of while loop comparing line1 and line3
				} // end of try/catch writing line2

				// OUTER ELSE CLAUSE WHERE LINE3 IS SMALLEST
			} else {

				// line3Index is smallest; write line3 to MesosticsCollated
				System.out.println(line3);
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(allMesosticsCollatedFileAddress), true));
				bw.write(line3);
				bw.newLine();
				bw.close();

				// advance and reformat line3
				try {
					line3 = br3.readLine();
					line3Array = line3.split("\t");
					line3Index = new Integer(line3Array[0]);
				} catch (NullPointerException e) {

					/*
					 * if no more of line3, continue with 2 line comparison of
					 * line1 and line2
					 * 
					 */
					while (line1 != null && line2 != null) {

						if (line1Index < line2Index) {

							/*
							 * line1Index is smallest; write line1 to
							 * MesosticsCollated
							 */

							System.out.println(line1);
							BufferedWriter bw2 = new BufferedWriter(
									new FileWriter(new File(allMesosticsCollatedFileAddress), true));
							bw2.write(line1);
							bw2.newLine();
							bw2.close();

							try {

								// advance and reformat line1
								line1 = br1.readLine();
								line1Array = line1.split("\t");
								line1Index = new Integer(line1Array[0]);

							} catch (NumberFormatException e1) {

								/*
								 * If no more line1 AND line3 write line2 to
								 * MesosticCollated to finish
								 * 
								 * Note: This exception should never occur
								 * because the index of the last line of the
								 * mesostics file is set at 10000
								 */

								if (line2 != null) {
									System.out.println(line2);
									BufferedWriter bw3 = new BufferedWriter(
											new FileWriter(new File(allMesosticsCollatedFileAddress), true));
									bw3.write(line2);
									bw3.newLine();
									bw3.close();
								}

							}

						} else {

							/*
							 * line2Index is smallest; write line2 to
							 * MesosticsCollated
							 */

							System.out.println(line2);
							BufferedWriter bw2 = new BufferedWriter(
									new FileWriter(new File(allMesosticsCollatedFileAddress), true));
							bw2.write(line2);
							bw2.newLine();
							bw2.close();

							// advance line2 to next line and reformat
							try {

								line2 = br3.readLine();
								line2Array = line2.split("\t");
								line2Index = new Integer(line2Array[0]);

							} catch (NullPointerException e1) {

								/*
								 * if no more of line2 write line1 to finish
								 */

								if (line1 != null) {

									System.out.println(line1);
									BufferedWriter bw1 = new BufferedWriter(
											new FileWriter(new File(allMesosticsCollatedFileAddress), true));
									bw1.write(line1);
									bw1.newLine();
									bw1.close();

								} // end of try catch finishing with line3
							} // end of else clause writing line2
						} // end of while loop comparing line1 and line2
					} // end while loop comparing line1 and line2
				} // end of try/catch writing line3
			} // END OF OUTER ELSE CLAUSE
		} // END OF WHILE LOOP FOR ALL THREE FILES

		// close resources
		br1.close();
		br2.close();
		br3.close();

	}

}
