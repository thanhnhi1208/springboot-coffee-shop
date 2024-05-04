package com.nhi.customer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhi.libary.model.Product;
import com.nhi.libary.repository.CartItemRepository;
import com.nhi.libary.repository.ProductRepository;

import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;

@Controller
public class ChatBotController {

	@Autowired
	private  ProductRepository productRepository;
	
	@Autowired
	private  CartItemRepository cartItemRepository;
	
	private  Map<String, String> questionAnswer = new HashMap<>();
	
	public  List<Product> findAllCartItemTopSeller() {
		List<Product> allProductTemp = new ArrayList<>();
		for (int productId : cartItemRepository.findAllCartItemTopSeller()) {
			allProductTemp.add(productRepository.findById(productId).get());
		}
		
		return allProductTemp;
	}
	
	public String formatVND(double price) {
        // Tạo một đối tượng NumberFormat với locale là tiếng Việt và định dạng kiểu tiền tệ
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        
        // Định dạng giá
        String formattedPrice = formatter.format(price);

        // Loại bỏ ký tự '₫' và thay thế bằng 'VNĐ'
        formattedPrice = formattedPrice.replace("₫", "VNĐ");

        return formattedPrice;
    }
	
	/*
	 * Define answers for each given category.
	 */
	public void getQueststion(String speech) {
		questionAnswer = new HashMap<>();
		questionAnswer.put("greeting", "Tôi có thể giúp gì cho bạn?");
		questionAnswer.put("conversation-continue", "Tôi có thể giúp gì khác cho bạn?");
		questionAnswer.put("conversation-complete", "Cảm ơn bạn, chúc bạn một ngày tốt lành");
		
		List<Product> allProductRecommend = findAllCartItemTopSeller();
		String allNameRecommend = "";
		allNameRecommend += "Xin mời bạn tham khảo " ;
		int countRecommend =0;
		for (Product product : allProductRecommend) {
			allNameRecommend += product.getName() + " có giá là: " + this.formatVND(product.getPrice());
			if(product.getSale_price() !=0) {
				allNameRecommend += " đang sale còn: "+this.formatVND(product.getSale_price());
			}
			
			if(countRecommend != allProductRecommend.size()-1) {
				allNameRecommend += ".";
			}
			
			countRecommend ++;
		}
		questionAnswer.put("recommend", allNameRecommend);
		
		List<Product> findAllHighestPrice = this.productRepository.findAllHighestPrice();
		String allNameHighestPrice = "Món giá cao nhất là: ";
		int countHighestPrice =0;
		if(findAllHighestPrice != null && findAllHighestPrice.isEmpty()==false) {
			for (Product product : findAllHighestPrice) {
				allNameHighestPrice += product.getName() + " có giá là: " + this.formatVND(product.getPrice());
				if(product.getSale_price() !=0) {
					allNameHighestPrice += " đang sale còn: "+this.formatVND(product.getSale_price());
				}
				
				if(countHighestPrice != findAllHighestPrice.size()-1) {
					allNameHighestPrice += ", ";
				}
				
				countHighestPrice ++;
			}
		}
		
		questionAnswer.put("price-inquiry", allNameHighestPrice);
		
		List<Product> findAllLowestPrice = this.productRepository.findAllLowestPrice();
		String allNameLowestPrice = "";
		int countLowestPrice =0;
		if(findAllLowestPrice != null && findAllLowestPrice.isEmpty()==false) {
			allNameLowestPrice += " | Món giá thấp nhất là: ";
			
			for (Product product : findAllLowestPrice) {
				allNameLowestPrice += product.getName() + " có giá là: " + this.formatVND(product.getPrice());
				if(product.getSale_price() !=0) {
					allNameLowestPrice += " đang sale còn: "+this.formatVND(product.getSale_price());
				}
				
				if(countLowestPrice != findAllLowestPrice.size()-1) {
					allNameLowestPrice += ", ";
				}
				
				countLowestPrice ++;
			}
		}
		
		
		questionAnswer.put("low-price", allNameLowestPrice);
		
		List<Product> findAllProductName = this.productRepository.findAllProductName(speech);
		int countFindAllProductNameString =0;
		String findAllProductNameString = "";
		for (Product product : findAllProductName) {
			findAllProductNameString += product.getName() + " có giá là: " + this.formatVND(product.getPrice());
			if(product.getSale_price() !=0) {
				findAllProductNameString += " đang sale còn: "+this.formatVND(product.getSale_price());
			}
			
			if(countFindAllProductNameString != findAllProductName.size()-1) {
				findAllProductNameString += ", ";
			}
			
			countFindAllProductNameString ++;
		}
		
		questionAnswer.put("drink", findAllProductNameString);

	}
	
	@GetMapping("/robot")
	@ResponseBody
	public String chatRobot(String speech) throws FileNotFoundException, IOException {
		if(speech != null) {
			speech = speech.toLowerCase();
		}
		this.getQueststion(speech);
		return robot(speech);
	}

	public String robot(String speech) throws FileNotFoundException, IOException {
		// Train categorizer model to the training data we created.
				DoccatModel model = trainCategorizerModel();
				System.out.println(model.getLanguage());
				// Take chat inputs from console (user) in a loop.
	
					// Get chat input from user.
					System.out.println("##### You:");
					String userInput = speech;

					// Break users chat input into sentences using sentence detection.
					String[] sentences = breakSentences(userInput);

					String answer = "";

					// Loop through sentences.
					for (String sentence : sentences) {

						// Separate words from each sentence using tokenizer.
						String[] tokens = tokenizeSentence(sentence);

						// Tag separated words with POS tags to understand their gramatical structure.
						String[] posTags = detectPOSTags(tokens);

						// Lemmatize each word so that its easy to categorize.
						String[] lemmas = lemmatizeTokens(tokens, posTags);

						// Determine BEST category using lemmatized tokens used a mode that we trained
						// at start.
						String category = detectCategory(model, lemmas);

						// Get predefined answer from given category & add to answer.
						answer = answer + " " + questionAnswer.get(category);

						// If category conversation-complete, we will end chat conversation.
						
					}

					// Print answer back to user. If conversation is marked as complete, then end
					// loop & program.
					return  answer;
					

				
	}

	/**
	 * Train categorizer model as per the category sample training data we created.
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static DoccatModel trainCategorizerModel() throws FileNotFoundException, IOException {
		// faq-categorizer.txt is a custom training data with categories as per our chat
		// requirements.
		InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("C:\\Users\\Admin\\Documents\\workspace-spring-tool-suite-4-4.20.1.RELEASE\\springboot-coffee_shop-root\\springboot-coffe_shop-customer\\src\\main\\resources\\training_data.txt"));
		ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
		ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

		DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });

		TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
		params.put(TrainingParameters.CUTOFF_PARAM, 0);

		// Train a model with classifications from above file.
		DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
		return model;
	}

	/**
	 * Detect category using given token. Use categorizer feature of Apache OpenNLP.
	 * 
	 * @param model
	 * @param finalTokens
	 * @return
	 * @throws IOException
	 */
	private static String detectCategory(DoccatModel model, String[] finalTokens) throws IOException {

		// Initialize document categorizer tool
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

		// Get best possible category.
		double[] probabilitiesOfOutcomes = myCategorizer.categorize(finalTokens);
		String category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
		System.out.println("Category: " + category);

		return category;

	}

	/**
	 * Break data into sentences using sentence detection feature of Apache OpenNLP.
	 * 
	 * @param data
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static String[] breakSentences(String data) throws FileNotFoundException, IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("C:\\Users\\Admin\\Documents\\workspace-spring-tool-suite-4-4.20.1.RELEASE\\springboot-coffee_shop-root\\springboot-coffe_shop-customer\\src\\main\\resources\\en-sent.bin")) {

			SentenceDetectorME myCategorizer = new SentenceDetectorME(new SentenceModel(modelIn));

			String[] sentences = myCategorizer.sentDetect(data);
			System.out.println("Sentence Detection: " + Arrays.stream(sentences).collect(Collectors.joining(" | ")));

			return sentences;
		}
	}

	/**
	 * Break sentence into words & punctuation marks using tokenizer feature of
	 * Apache OpenNLP.
	 * 
	 * @param sentence
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static String[] tokenizeSentence(String sentence) throws FileNotFoundException, IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("C:\\Users\\Admin\\Documents\\workspace-spring-tool-suite-4-4.20.1.RELEASE\\springboot-coffee_shop-root\\springboot-coffe_shop-customer\\src\\main\\resources\\en-token.bin")) {

			// Initialize tokenizer tool
			TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(modelIn));

			// Tokenize sentence.
			String[] tokens = myCategorizer.tokenize(sentence);
			System.out.println("Tokenizer : " + Arrays.stream(tokens).collect(Collectors.joining(" | ")));

			return tokens;

		}
	}

	/**
	 * Find part-of-speech or POS tags of all tokens using POS tagger feature of
	 * Apache OpenNLP.
	 * 
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private static String[] detectPOSTags(String[] tokens) throws IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("C:\\Users\\Admin\\Documents\\workspace-spring-tool-suite-4-4.20.1.RELEASE\\springboot-coffee_shop-root\\springboot-coffe_shop-customer\\src\\main\\resources\\en-pos-maxent.bin")) {

			// Initialize POS tagger tool
			POSTaggerME myCategorizer = new POSTaggerME(new POSModel(modelIn));

			// Tag sentence.
			String[] posTokens = myCategorizer.tag(tokens);
			System.out.println("POS Tags : " + Arrays.stream(posTokens).collect(Collectors.joining(" | ")));

			return posTokens;

		}

	}

	/**
	 * Find lemma of tokens using lemmatizer feature of Apache OpenNLP.
	 * 
	 * @param tokens
	 * @param posTags
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	private static String[] lemmatizeTokens(String[] tokens, String[] posTags)
			throws InvalidFormatException, IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("C:\\Users\\Admin\\Documents\\workspace-spring-tool-suite-4-4.20.1.RELEASE\\springboot-coffee_shop-root\\springboot-coffe_shop-customer\\src\\main\\resources\\en-lemmatizer.bin")) {

			// Tag sentence.
			LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(modelIn));
			String[] lemmaTokens = myCategorizer.lemmatize(tokens, posTags);
			System.out.println("Lemmatizer : " + Arrays.stream(lemmaTokens).collect(Collectors.joining(" | ")));

			return lemmaTokens;

		}
	}
}
