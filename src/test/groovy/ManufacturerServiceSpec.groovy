import com.taxi.exception.DataProcessingException
import com.taxi.model.Manufacturer
import com.taxi.services.ManufacturerServiceImpl
import com.taxi.services.interfaces.ManufacturerService
import spock.lang.Specification

class ManufacturerServiceSpec extends Specification{
    def "should create Manufacturer with name and country"() {
        given:
        ManufacturerService manufacturerService = Mock(ManufacturerService)
        def manufacturer = new Manufacturer(0, "Dodge", "Canada", false)
        manufacturerService.create(manufacturer) >> manufacturer

        when:
        def result = manufacturerService.create(manufacturer)

        then:
        result.id == 0
        result.name == "Dodge"
        result.country == "Canada"
        result.deleted == false
    }

//    def "should throw DataProcessingException with message"() {
//        when:
//        new Manufacturer(-1, null, null, true)
//
//        then:
//        thrown(DataProcessingException)
//        String expectedMessage = "Invalid arguments"
//        thrown(expectedMessage)
//    }
}
