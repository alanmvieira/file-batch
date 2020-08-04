Feature: Compile sales data files into a sale report

  Scenario: Process a valid sales file
    Given a sales file "test_file.dat" with the following content exists
      """
      001ç1234567891234çPedroç50000
      001ç3245678865434çPauloç40000.99
      002ç2345675434544345çJose da SilvaçRural
      002ç2345675433444345çEduardo PereiraçRural
      003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
      003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
      """
    When the application runs
    Then a sales report "test_file.done.dat" with the following content should exist
      """
      Total customer count: 2
      Total salesman count: 2
      ID most expensive sale: 10
      Worst salesman: Paulo
      """
    And a file "test_file.dat" should exist on the success folder

  Scenario: Process sales file with invalid content
    Given a sales file "test_file.dat" with the following content exists
      """
      001çPedroç50000
      001ç3245678865434çPauloç40000.99
      002ç2345675434544345çJose da SilvaçRural
      002ç2345675433444345çEduardo PereiraçRural
      003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
      003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
      """
    When the application runs
    Then a file "test_file.dat" should exist on the error folder

  Scenario: Process sales file with invalid type
    Given a sales file "test_file.txt" with the following content exists
      """
      001ç1234567891234çPedroç50000
      001ç3245678865434çPauloç40000.99
      002ç2345675434544345çJose da SilvaçRural
      002ç2345675433444345çEduardo PereiraçRural
      003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
      003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
      """
    When the application runs
    Then a file "test_file.txt" should exist on the error folder

  Scenario: Process sales file with invalid line code
    Given a sales file "test_file.dat" with the following content exists
      """
      004ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
      """
    When the application runs
    Then a file "test_file.dat" should exist on the error folder