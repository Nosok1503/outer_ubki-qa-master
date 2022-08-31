package ua.ubki.outertest;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.http.HttpRequestActionBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.ubki.provider.PropertiesProvider;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class TestSh11_ListINNSimulation extends Simulation {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSh11_ListINNSimulation.class);

    private List<String> listINN = new ArrayList<>();

    Integer maxDelayTime10Sh = 3000;
    Integer maxDelayTime11Sh = 3000;

    Integer varresponseTime1 = 1000000;
    Integer varresponseTime2 = 1000000;
    Integer varresponseTime3 = 1000000;
    Integer varresponseTime4 = 1000000;
    Integer varresponseTime5 = 1000000;
    Integer varresponseTime6 = 1000000;


    Boolean commandExit = false;
    Boolean statusCodeNot200 = false;
    LocalDateTime requestTime1 = LocalDateTime.now();
    LocalDateTime requestTime2 = LocalDateTime.now();
    LocalDateTime requestTime3 = LocalDateTime.now();
    LocalDateTime requestTime4 = LocalDateTime.now();
    LocalDateTime requestTime5 = LocalDateTime.now();
    LocalDateTime requestTime6 = LocalDateTime.now();

    String kodRequest1 = "";
    String kodRequest2 = "";
    String kodRequest3 = "";
    String kodRequest4 = "";
    String kodRequest5 = "";
    String kodRequest6 = "";

    String shablonRequest1;
    String shablonRequest2;
    String shablonRequest3;
    String shablonRequest4;
    String shablonRequest5;
    String shablonRequest6;

    Boolean requestError1;
    Boolean requestError2;
    Boolean requestError3;
    Boolean requestError4;
    Boolean requestError5;
    Boolean requestError6;

    String textRequestError1 = "";
    String textRequestError2 = "";
    String textRequestError3 = "";
    String textRequestError4 = "";
    String textRequestError5 = "";
    String textRequestError6 = "";

    String textResponceError1 = "";
    String textResponceError2 = "";
    String textResponceError3 = "";
    String textResponceError4 = "";
    String textResponceError5 = "";
    String textResponceError6 = "";


    String ubkiSession = "";
    String today = java.time.LocalDate.now().minusDays(0).toString();
    String yesterday = java.time.LocalDate.now().minusDays(1).toString();
    String dayBeforeYesterday = java.time.LocalDate.now().minusDays(2).toString();
    private String csvPath;


    @Override
    public void before() {
        LOGGER.info("Tests begin");
        PropertiesProvider ubkiProperties = PropertiesProvider.getInstance("outertest.properties");
        csvPath = ubkiProperties.getPropertyAsString("path.to.csv", "C://ubki//GitLab//outertest-qa//src//docs");
//        readDataLineByLine(csvPath + "/data/ListIPN.csv");

 /*       String inn1 = listINN.get(RandomUtils.nextInt(0, listINN.size()));
        String inn2 = listINN.get(RandomUtils.nextInt(0, listINN.size()));
        String inn3 = listINN.get(RandomUtils.nextInt(0, listINN.size()));
        String inn4 = listINN.get(RandomUtils.nextInt(0, listINN.size()));
        String inn5 = listINN.get(RandomUtils.nextInt(0, listINN.size()));
        String inn6 = listINN.get(RandomUtils.nextInt(0, listINN.size()));
*/

    }


    public void readDataLineByLine(String file) {

        try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;


            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                InnDTO innDto = new InnDTO()
                        .setInn(nextRecord[0]);
                //                       .setDate(LocalDate.parse(nextRecord[1]))
                //                       .setProcessed(Boolean.parseBoolean(nextRecord[2]));
                listINN.add(innDto.getInn());

                System.out.println(innDto);
            }
            // тут є список інн з датами

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;


    public static void writeDataLineByLine(String filePath, String[] newRow) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            if (!file.exists()) {
                FileUtils.writeLines(file, Collections.emptyList());
            }
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            //           String[] header = {"Name", "Class", "Marks"};
//            writer.writeNext(header);

            // add data to csv
            writer.writeNext(newRow);
/*
            String[] data2 = {"Suraj", "10", "630"};
            writer.writeNext(data2);
*/

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


//        LOGGER.info("Clean DBs before tests FINISHED");


    @Override
    public void after() {

 /*       if (varresponseTime1 < varresponseTime2 && varresponseTime1 < varresponseTime3) {
            writeDataLineByLine(csvPath + "/log/LogUptimeSh10.csv", new String[]{requestTime1.format(DateTimeFormatter.ISO_DATE_TIME), String.valueOf(varresponseTime1), kodRequest1, shablonRequest1, textRequestError1});
        } else {
            if (varresponseTime2 < varresponseTime1 && varresponseTime2 < varresponseTime3) {
                writeDataLineByLine(csvPath + "/log/LogUptimeSh10.csv", new String[]{requestTime2.format(DateTimeFormatter.ISO_DATE_TIME), String.valueOf(varresponseTime2), kodRequest2, shablonRequest2, textRequestError2});
            } else {
                writeDataLineByLine(csvPath + "/log/LogUptimeSh10.csv", new String[]{requestTime3.format(DateTimeFormatter.ISO_DATE_TIME), String.valueOf(varresponseTime3), kodRequest3, shablonRequest3, textRequestError3});
            }

        }
*/
        if (varresponseTime4 < varresponseTime5 && varresponseTime4 < varresponseTime6) {
            writeDataLineByLine(csvPath + "/log/LogUptimeSh11.csv", new String[]{String.valueOf(requestTime4.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"))), String.valueOf(varresponseTime4), kodRequest4, shablonRequest4, textRequestError4});
        } else {
            if (varresponseTime5 < varresponseTime4 && varresponseTime5 < varresponseTime6) {
                writeDataLineByLine(csvPath + "/log/LogUptimeSh11.csv", new String[]{String.valueOf(requestTime5.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"))), String.valueOf(varresponseTime5), kodRequest5, shablonRequest5, textRequestError5});
            } else {
                writeDataLineByLine(csvPath + "/log/LogUptimeSh11.csv", new String[]{String.valueOf(requestTime6.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"))), String.valueOf(varresponseTime6), kodRequest6, shablonRequest6, textRequestError6});
            }

        }

        if (!(textResponceError4 == "")) {
            writeDataLineByLine(csvPath + "/log/LogErrUptimeSh11.csv", new String[]{requestTime4.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")), String.valueOf(varresponseTime4), shablonRequest4, textResponceError4});
        }
        ;
        if (!(textResponceError5 == "")) {
            writeDataLineByLine(csvPath + "/log/LogErrUptimeSh11.csv", new String[]{requestTime5.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")), String.valueOf(varresponseTime5), shablonRequest5, textResponceError5});
        }
        ;
        if (!(textResponceError6 == "")) {
            writeDataLineByLine(csvPath + "/log/LogErrUptimeSh11.csv", new String[]{requestTime6.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")), String.valueOf(varresponseTime6), shablonRequest6, textResponceError6});
        }
        ;


        LOGGER.info("The test is finished");
    }

    //String randomINN = listINN.get(RandomUtils.nextInt(0, listINN.size()));

    String currentMonth = String.valueOf(java.time.LocalDate.now().getMonthValue());
    String currentYear = String.valueOf(java.time.LocalDate.now().getYear());


    HttpRequestActionBuilder authHttp = http("auth")
//            .post("https://test.ubki.ua/b2_api_xml/ubki/auth")
            .post("https://secure.ubki.ua/b2_api_xml/ubki/auth")
            .transformResponse((response, session) -> {
                if (response.status().code() == 200) {
                    LOGGER.info("Response: {}", response.body().string());
                    return response;
                } else {
                    return response;
                }
            })
            .header("accept", "application/json")
            .header("content-type", "application/xml")
            .body(StringBody("{\"doc\": {\"auth\": { \"login\" : \"Avtotest\", \"pass\": " +
                    "\"Avtotest2022!\" }" +
                    " } }"));
/*
            .body(StringBody("{\"doc\": {\"auth\": { \"login\" : \"QAEnginerUBKI\", \"pass\": " +
            "\"QAEngubki2021!\" }" +
            " } }"));
*/

//curl -H "Accept: application/json" "https://secure.ubki.ua/b2_api_xml/ubki/auth" -d "{\"doc\": {\"auth\": { \"login\" : \"Avtotest\", \"pass\": \"Avtotest2022!\" } } }"

    ScenarioBuilder authScn = scenario("AuthToUbki")
            .exec(authHttp
                    .check(status().is(200), xpath("/doc/auth/@sessid").find().saveAs("sessid")))
            .exitHereIfFailed()
            .exec(session -> {
                ubkiSession = session.getString("sessid");
                session.set("ubkiSession", session.getString("sessid"));
                return session;
            });


    HttpProtocolBuilder httpProtocolB2 = http
            .baseUrl("https://secure.ubki.ua/b2_api_xml/ubki") // Here is the root for all relative URLs
            //.baseUrl("https://test.ubki.ua/b2_api_xml/ubki") // Here is the root for all relative URLs

            .acceptHeader("application/xml")
            .contentTypeHeader("application/xml")
            .authorizationHeader(session -> session.getString("sessid"))
            .userAgentHeader("Gatling outer ListINN Test Shablon 10,11 ");


    HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://secure.ubki.ua/upload/data") // Here is the root for all relative URLs
            //.baseUrl("https://test.ubki.ua/upload/data") // Here is the root for all relative URLs

            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
            .authorizationHeader(session -> session.getString("sessid"))
            .userAgentHeader("Gatling outer ListINN Test w2");


    ScenarioBuilder CheckB2Shablon10ForListINN = scenario("Check Shablon 10 B2 for List INN")
            .exec(session -> session.set("sessid", ubkiSession))
//            .exec(session -> session.set("responseTime1", 6000))
//            .exec(session -> session.set("responseTime2", 6000))
//            .exec(session -> session.set("responseTime3", 6000))

//Запит №1 Шаблон 10
//listINN\.get\(RandomUtils\.nextInt\(0\, listINN\.size\(\)\)\)\)
            .exec(session -> session.set("inn", "**RANDOM**"))
            .exec(http("Check B2 Shablon 10 for INN1")
                    .post("/xml")

                    .body(ElFileBody("data/Requvest10Shablon.xml"))
                    .transformResponse((response, session) -> {
                        if (response.status().code() == 200) {
                            statusCodeNot200 = false;
                            //LOGGER.info("Response w2 Sh10 Outer Simulation for List INN1: {} ", response.body().string());
                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                requestError1 = true;
                                textRequestError1 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                            } else {
                                requestError1 = false;
                                textRequestError1 = "";
                                kodRequest1 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");

                            }
                            LOGGER.info("requestError1: {}", requestError1);
                            LOGGER.info("textRequestError1: {}", textRequestError1);

                        } else {
                            requestError1 = true;
                            textRequestError1 = String.valueOf(response.status().code());
                            statusCodeNot200 = true;
                        }
                        requestTime1 = LocalDateTime.now();
                        shablonRequest1 = "10";
                        LOGGER.info("inn1: {}", session.getString("inn"));

                        return response;
                    })
                    .check(responseTimeInMillis().saveAs("responseTime1"))

            )
            .exec(session -> {
                if (statusCodeNot200) {
                    varresponseTime1 = 1000000;
                } else {
                    varresponseTime1 = session.getInt("responseTime1");
                    LOGGER.info("responseTime1: {} ", varresponseTime1);
                }
                return session;
            })
            .exec(session -> {
                LOGGER.info("varresponseTime1 < maxDelayTime && !requestError1: {} ", varresponseTime1 < maxDelayTime10Sh && !requestError1);
                //               commandExit=varresponseTime1 < maxDelayTime && !requestError1;
                return session.set("commandExit", varresponseTime1 < maxDelayTime10Sh && !requestError1);
            })
            .exitHereIf(session -> {
                LOGGER.info("commandExit: {} ", session.getBoolean("commandExit"));
                return session.getBoolean("commandExit");
            })

//Запит №2  Шаблон 10
            .exec(session -> session.set("inn", "**RANDOM**"))
            .exec(http("Check B2 Shablon 10 for INN2")
                    .post("/xml")

                    .body(ElFileBody("data/Requvest10Shablon.xml"))
                    .transformResponse((response, session) -> {
                        if (response.status().code() == 200) {
                            statusCodeNot200 = false;
                            //LOGGER.info("Response w2 Sh10 Outer Simulation for List INN2: {} ", response.body().string());
                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                requestError2 = true;
                                textRequestError2 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                            } else {
                                requestError2 = false;
                                textRequestError2 = "";
                                kodRequest2 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");

                            }
                            LOGGER.info("requestError2: {}", requestError2);
                            LOGGER.info("textRequestError2: {}", textRequestError2);

                        } else {
                            requestError2 = true;
                            textRequestError2 = String.valueOf(response.status().code());
                            statusCodeNot200 = true;
                        }
                        requestTime2 = LocalDateTime.now();
                        shablonRequest2 = "10";
                        LOGGER.info("inn2: {}", session.getString("inn"));

                        return response;
                    })
                    .check(responseTimeInMillis().saveAs("responseTime2"))

            )
            .exec(session -> {
                if (statusCodeNot200) {
                    varresponseTime2 = 1000000;
                } else {
                    varresponseTime2 = session.getInt("responseTime2");
                    LOGGER.info("responseTime2: {} ", varresponseTime2);
                }
                return session;
            })
            .exec(session -> {

                LOGGER.info("varresponseTime2 < maxDelayTime && !requestError2: {} ", varresponseTime2 < maxDelayTime10Sh && !requestError2);
                return session.set("commandExit", varresponseTime2 < maxDelayTime10Sh && !requestError2);
            })
            .exitHereIf(session -> {
                LOGGER.info("commandExit: {} ", session.getBoolean("commandExit"));
                return session.getBoolean("commandExit");
            })
//Запит №3  Шаблон 10
            .exec(session -> session.set("inn", "**RANDOM**"))
            .exec(http("Check B2 Shablon 10 for INN3")
                    .post("/xml")

                    .body(ElFileBody("data/Requvest10Shablon.xml"))
                    .transformResponse((response, session) -> {
                        if (response.status().code() == 200) {
                            statusCodeNot200 = false;
                            //LOGGER.info("Response w2 Sh10 Outer Simulation for List INN3: {} ", response.body().string());
                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                requestError3 = true;
                                textRequestError3 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                            } else {
                                requestError3 = false;
                                textRequestError3 = "";
                                kodRequest3 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");

                            }
                            LOGGER.info("requestError3: {}", requestError3);
                            LOGGER.info("textRequestError3: {}", textRequestError3);

                        } else {
                            requestError3 = true;
                            textRequestError3 = String.valueOf(response.status().code());
                            statusCodeNot200 = true;
                        }
                        requestTime3 = LocalDateTime.now();
                        shablonRequest3 = "10";
                        LOGGER.info("inn3: {}", session.getString("inn"));

                        return response;
                    })
                    .check(responseTimeInMillis().saveAs("responseTime3"))

            )
            .exec(session -> {
                if (statusCodeNot200) {
                    varresponseTime3 = 1000000;
                } else {
                    varresponseTime3 = session.getInt("responseTime3");
                    LOGGER.info("responseTime3: {} ", varresponseTime3);
                }
                return session;
            });


    ScenarioBuilder CheckB2Shablon11ForListINN = scenario("Check Shablon 11 B2 for List INN")
            .exec(session -> session.set("sessid", ubkiSession))

//Запит №4 Шаблон 11
            .exec(session -> session.set("inn", "**RANDOM**"))
            .exec(http("Check B2 Shablon 11 for INN4")
                    .post("/xml")

                    .body(ElFileBody("data/Requvest11Shablon.xml"))
                    .transformResponse((response, session) -> {
//                         LOGGER.info("Response w2 Sh11 Outer Simulation for List INN4: {} ", response.body().string());

                        if (response.status().code() == 200) {
                            statusCodeNot200 = false;
                            //                           LOGGER.info("Response w2 Sh10 Outer Simulation for List INN4: {} ", response.body().string());
                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                requestError4 = true;
                                textRequestError4 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                                if (textRequestError4 == "") {
                                    textResponceError4 = response.body().string();
                                }
                            } else {
                                requestError4 = false;
                                textRequestError4 = "";
                                kodRequest4 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");
                                if (kodRequest4 == "") {
                                    textResponceError4 = response.body().string();
                                }


                            }
                            LOGGER.info("requestError4: {}", requestError4);
                            LOGGER.info("textRequestError4: {}", textRequestError4);

                        } else {
                            requestError4 = true;
                            textRequestError4 = String.valueOf(response.status().code());
                            statusCodeNot200 = true;
                            if (textRequestError4 == "") {
                                textResponceError4 = response.body().string();
                            }
                        }
                        requestTime4 = LocalDateTime.now();
                        shablonRequest4 = "11";
                        LOGGER.info("inn4: {}", session.getString("inn"));

                        return response;
                    })
                    .check(responseTimeInMillis().saveAs("responseTime4"))

            )
            .exec(session -> {
                if (statusCodeNot200) {
                    varresponseTime4 = 1000000;
                } else {
                    varresponseTime4 = session.getInt("responseTime4");
                    LOGGER.info("responseTime4: {} ", varresponseTime4);
                }
                return session;
            })
            .exec(session -> {
                LOGGER.info("varresponseTime4 < maxDelayTime && !requestError4: {} ", varresponseTime4 < maxDelayTime11Sh && !requestError4);
                return session.set("commandExit", varresponseTime4 < maxDelayTime11Sh && !requestError4);
            })
            .exitHereIf(session -> {
                LOGGER.info("commandExit: {} ", session.getBoolean("commandExit"));
                return session.getBoolean("commandExit");
            })
//Запит №5  Шаблон 11
            .exec(session -> session.set("inn", "**RANDOM**"))
            .exec(http("Check B2 Shablon 11 for INN5")
                    .post("/xml")

                    .body(ElFileBody("data/Requvest11Shablon.xml"))
                    .transformResponse((response, session) -> {
                        if (response.status().code() == 200) {
                            statusCodeNot200 = false;
                            //LOGGER.info("Response w2 Sh10 Outer Simulation for List INN5: {} ", response.body().string());
                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                requestError5 = true;
                                textRequestError5 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                                if (textRequestError5 == "") {
                                    textResponceError5 = response.body().string();
                                }
                            } else {
                                requestError5 = false;
                                textRequestError5 = "";
                                kodRequest5 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");
                                if (kodRequest5 == "") {
                                    textResponceError5 = response.body().string();
                                }

                            }
                            LOGGER.info("requestError5: {}", requestError5);
                            LOGGER.info("textRequestError5: {}", textRequestError5);

                        } else {
                            requestError5 = true;
                            textRequestError5 = String.valueOf(response.status().code());
                            statusCodeNot200 = true;
                            if (textRequestError5 == "") {
                                textResponceError5 = response.body().string();
                            }
                        }
                        requestTime5 = LocalDateTime.now();
                        shablonRequest5 = "11";
                        LOGGER.info("inn5: {}", session.getString("inn"));

                        return response;
                    })
                    .check(responseTimeInMillis().saveAs("responseTime5"))

            )
            .exec(session -> {
                if (statusCodeNot200) {
                    varresponseTime5 = 1000000;
                } else {
                    varresponseTime5 = session.getInt("responseTime5");
                    LOGGER.info("responseTime5: {} ", varresponseTime5);
                }
                return session;
            })
            .exec(session -> {
                LOGGER.info("varresponseTime5 < maxDelayTime && !requestError5: {} ", varresponseTime5 < maxDelayTime11Sh && !requestError5);
                return session.set("commandExit", varresponseTime5 < maxDelayTime11Sh && !requestError5);
            })
            .exitHereIf(session -> {
                LOGGER.info("commandExit: {} ", session.getBoolean("commandExit"));
                return session.getBoolean("commandExit");
            })
//Запит №6  Шаблон 11
            .exec(session -> session.set("inn", "**RANDOM**"))
            .exec(http("Check B2 Shablon 11 for INN6")
                    .post("/xml")

                    .body(ElFileBody("data/Requvest11Shablon.xml"))
                    .transformResponse((response, session) -> {
                        if (response.status().code() == 200) {
                            statusCodeNot200 = false;
                            //LOGGER.info("Response w2 Sh10 Outer Simulation for List INN3: {} ", response.body().string());
                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                requestError6 = true;
                                textRequestError6 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                                if (textRequestError6 == "") {
                                    textResponceError6 = response.body().string();
                                }
                            } else {
                                requestError6 = false;
                                textRequestError6 = "";
                                kodRequest6 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");
                                if (kodRequest6 == "") {
                                    textResponceError6 = response.body().string();
                                }


                            }
                            LOGGER.info("requestError6: {}", requestError6);
                            LOGGER.info("textRequestError6: {}", textRequestError6);

                        } else {
                            requestError6 = true;
                            textRequestError6 = String.valueOf(response.status().code());
                            statusCodeNot200 = true;
                            if (textRequestError6 == "") {
                                textResponceError6 = response.body().string();
                            }
                        }
                        requestTime6 = LocalDateTime.now();
                        shablonRequest6 = "11";
                        LOGGER.info("inn6: {}", session.getString("inn"));

                        return response;
                    })
                    .check(responseTimeInMillis().saveAs("responseTime6"))

            )
            .exec(session -> {
                if (statusCodeNot200) {
                    varresponseTime6 = 1000000;
                } else {
                    varresponseTime6 = session.getInt("responseTime6");
                    LOGGER.info("responseTime6: {} ", varresponseTime6);
                }
                return session;
            });


    ChainBuilder browse =
            repeat(5, "n").on( // 1
                    exec(http("Page #{n}").get("/computers?p=#{n}")) // 2
                            .pause(1)
            );


    //ScenarioBuilder CheckB2Shablon11ForListINN60minutNew = scenario("Check Shablon 11 B2 for List INN")


    ScenarioBuilder CheckB2Shablon11ForListINN60minut = scenario("Check Shablon 11 B2 for List INN")
            .repeat(5).on(
                    exec(session -> session.set("sessid", ubkiSession))
//Запит №4 Шаблон 11
                            .exec(session -> session.set("inn", "**RANDOM**"))
                            .exec(http("Check B2 Shablon 11 for INN4")
                                    .post("/xml")

                                    .body(ElFileBody("data/Requvest11Shablon.xml"))
                                    .transformResponse((response, session) -> {
                                        //                                       LOGGER.info("Response w2 Sh11 Outer Simulation for List INN4: {} ", response.body().string());

                                        if (response.status().code() == 200) {
                                            statusCodeNot200 = false;
//                                            LOGGER.info("Response w2 Sh11 Outer Simulation for List INN4: {} ", response.body().string());
                                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                                requestError4 = true;
                                                textRequestError4 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                                            } else {
                                                requestError4 = false;
                                                textRequestError4 = "";
                                                kodRequest4 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");
                                            }
                                            LOGGER.info("requestError4: {}", requestError4);
                                            LOGGER.info("textRequestError4: {}", textRequestError4);

                                        } else {
                                            //                                           LOGGER.info("Response w2 Sh11 Outer Simulation for List INN4: {} ", response.body().string());
                                            requestError4 = true;
                                            textRequestError4 = String.valueOf(response.status().code());
                                            statusCodeNot200 = true;
                                        }
                                        requestTime4 = LocalDateTime.now();
                                        shablonRequest4 = "11";
                                        LOGGER.info("inn4: {}", session.getString("inn"));

                                        return response;
                                    })
                                    .check(responseTimeInMillis().saveAs("responseTime4"))

                            )
                            .exec(session -> {
                                if (statusCodeNot200) {
                                    varresponseTime4 = 1000000;
                                } else {
                                    varresponseTime4 = session.getInt("responseTime4");
                                    LOGGER.info("responseTime4: {} ", varresponseTime4);
                                }
                                return session;
                            })
                            .exec(session -> {
                                LOGGER.info("varresponseTime4 < maxDelayTime && !requestError4: {} ", varresponseTime4 < maxDelayTime11Sh && !requestError4);
                                return session.set("commandExit", varresponseTime4 < maxDelayTime11Sh && !requestError4);
                            })
                            .exitHereIf(session -> {
                                LOGGER.info("commandExit: {} ", session.getBoolean("commandExit"));
                                return session.getBoolean("commandExit");
                            })
//Запит №5  Шаблон 11
                            .exec(session -> session.set("inn", "**RANDOM**"))
                            .exec(http("Check B2 Shablon 11 for INN5")
                                    .post("/xml")

                                    .body(ElFileBody("data/Requvest11Shablon.xml"))
                                    .transformResponse((response, session) -> {
                                        if (response.status().code() == 200) {
                                            statusCodeNot200 = false;
                                            //LOGGER.info("Response w2 Sh10 Outer Simulation for List INN5: {} ", response.body().string());
                                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                                requestError5 = true;
                                                textRequestError5 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                                            } else {
                                                requestError5 = false;
                                                textRequestError5 = "";
                                                kodRequest5 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");

                                            }
                                            LOGGER.info("requestError2: {}", requestError5);
                                            LOGGER.info("textRequestError2: {}", textRequestError5);

                                        } else {
                                            requestError5 = true;
                                            textRequestError5 = String.valueOf(response.status().code());
                                            statusCodeNot200 = true;
                                        }
                                        requestTime5 = LocalDateTime.now();
                                        shablonRequest5 = "11";
                                        LOGGER.info("inn5: {}", session.getString("inn"));

                                        return response;
                                    })
                                    .check(responseTimeInMillis().saveAs("responseTime5"))

                            )
                            .exec(session -> {
                                if (statusCodeNot200) {
                                    varresponseTime5 = 1000000;
                                } else {
                                    varresponseTime5 = session.getInt("responseTime5");
                                    LOGGER.info("responseTime5: {} ", varresponseTime5);
                                }
                                return session;
                            })
                            .exec(session -> {
                                LOGGER.info("varresponseTime5 < maxDelayTime && !requestError5: {} ", varresponseTime5 < maxDelayTime11Sh && !requestError5);
                                return session.set("commandExit", varresponseTime5 < maxDelayTime11Sh && !requestError5);
                            })
                            .exitHereIf(session -> {
                                LOGGER.info("commandExit: {} ", session.getBoolean("commandExit"));
                                return session.getBoolean("commandExit");
                            })
//Запит №6  Шаблон 11
                            .exec(session -> session.set("inn", "**RANDOM**"))
                            .exec(http("Check B2 Shablon 11 for INN6")
                                    .post("/xml")

                                    .body(ElFileBody("data/Requvest11Shablon.xml"))
                                    .transformResponse((response, session) -> {
                                        if (response.status().code() == 200) {
                                            statusCodeNot200 = false;
                                            //LOGGER.info("Response w2 Sh10 Outer Simulation for List INN3: {} ", response.body().string());
                                            if ((response.body().string().indexOf("<error", 0) > 0) & (response.body().string().indexOf("errtext", 0) > 0) & (response.body().string().indexOf("errtype=", 0) > 0)) {
                                                requestError6 = true;
                                                textRequestError6 = StringUtils.substringBetween(response.body().string(), "errtype=\"", "\"");
                                            } else {
                                                requestError6 = false;
                                                textRequestError6 = "";
                                                kodRequest6 = StringUtils.substringBetween(response.body().string(), "reqinfo reqid=\"", "\"");

                                            }
                                            LOGGER.info("requestError6: {}", requestError6);
                                            LOGGER.info("textRequestError6: {}", textRequestError6);

                                        } else {
                                            requestError6 = true;
                                            textRequestError6 = String.valueOf(response.status().code());
                                            statusCodeNot200 = true;
                                        }
                                        requestTime6 = LocalDateTime.now();
                                        shablonRequest6 = "11";
                                        LOGGER.info("inn6: {}", session.getString("inn"));

                                        return response;
                                    })
                                    .check(responseTimeInMillis().saveAs("responseTime6"))

                            )
                            .exec(session -> {
                                if (statusCodeNot200) {
                                    varresponseTime6 = 1000000;
                                } else {
                                    varresponseTime6 = session.getInt("responseTime6");
                                    LOGGER.info("responseTime6: {} ", varresponseTime6);
                                }
                                return session;
                            })


            );


    ScenarioBuilder CheckB2Shablon11Once = scenario("Check Shablon 11 B2 for List INN")
            .exec(session -> session.set("sessid", ubkiSession))
            .exec(session -> session.set("inn", "**RANDOM**"))
            .exec(http("Check B2 Shablon 11 for List INN")
                    .post("/xml")


                    .body(ElFileBody("data/Requvest11Shablon.xml"))
                    .transformResponse((response, session) -> {
                        if (response.status().code() == 200) {
                            LOGGER.info("Response w2 Sh11 Outer Simulation for List INN: {} ", response.body().string());
                            return response;
                        } else {
                            LOGGER.info("Response w2 Sh11 Outer Simulation for List INN: {}", response.body().string());
                            return response;
                        }
                    })
                    .check(status().in(200))
                    .check(substring("inn=\"3297003038\"").exists())
            );


    ScenarioBuilder w2OuterTestIPNfromList = scenario("w2OuterTestIPNfromList Add Ident And Deals")
            //     .feed(Array(buildIdent("3297003038","2")).queue)
            .exec(session -> session.set("sessid", ubkiSession))
            .exec(session -> session.set("inn", "**RAN**"))
            .exec(session -> session.set("vdate", today))
            .exec(session -> session.set("currentYear", currentYear))
            .exec(session -> session.set("currentMonth", currentMonth))

            .exec(http("W2: w2OuterTestIPNfromList Add Ident And Deals")
                    .post("/xml")
                    .body(ElFileBody("data/ok_OuterPoligonTestW2B2_IPNfromList.xml"))
                    .transformResponse((response, session) -> {
                        if (response.status().code() == 200) {

                            LOGGER.info("Response W2 w2OuterTestIPNfromList: {}", response.body().string());
                            return response;
                        } else {
                            LOGGER.info("Response W2 w2OuterTestIPNfromList: {}", response.body().string());
                            return response;
                        }
                    })
                    .check(status().in(200))
                    .check(substring("er=\"0\"").exists())
                    .check(substring("dlref=\"ТестРаботоспособностиW2B2 IPNfromList " + today + "\"").exists())
            );

    {
        setUp(authScn.injectOpen(atOnceUsers(1)).protocols()
                        //               .andThen(CheckB2Shablon10ForListINN.injectOpen(atOnceUsers(1)).protocols(httpProtocolB2)
                        .andThen(CheckB2Shablon11ForListINN.injectOpen(atOnceUsers(1)).protocols(httpProtocolB2)


                        )


                //               )
        )
                .assertions(global().failedRequests().count().lt(1L));

    }
}

