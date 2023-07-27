Adding Spock Framework + Groovy tests.
1. add dependencies 
        <dependency>
            <groupId>org.spockframework</groupId>
            <artifactId>spock-core</artifactId>
            <version>1.0-groovy-2.4</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>2.4.7</version>
            <scope>test</scope>
        </dependency>
2. add plugin
   <plugin>
   <groupId>org.codehaus.gmavenplus</groupId>
   <artifactId>gmavenplus-plugin</artifactId>
   <version>1.5</version>
   <executions>
   <execution>
   <goals>
   <goal>compile</goal>
   <goal>testCompile</goal>
   </goals>
   </execution>
   </executions>
   </plugin>
3. Create Service tests: ManufacturerServiceTest, DriverServiceTest, CarServiceTest.