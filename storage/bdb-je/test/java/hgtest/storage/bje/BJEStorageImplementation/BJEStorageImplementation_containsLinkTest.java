package hgtest.storage.bje.BJEStorageImplementation;

import com.sleepycat.je.DatabaseNotFoundException;
import org.hypergraphdb.HGException;
import org.hypergraphdb.HGPersistentHandle;
import org.hypergraphdb.handle.UUIDPersistentHandle;
import org.testng.annotations.Test;

import static hgtest.TestUtils.assertExceptions;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Yuriy Sechko
 */
public class BJEStorageImplementation_containsLinkTest extends
		BJEStorageImplementationTestBasis
{

	@Test
	public void checkExistenceOfLinkUsingNullHandle() throws Exception
	{
		startup();
		try
		{
			storage.containsLink(null);
		}
		catch (Exception ex)
		{
			assertEquals(ex.getClass(), NullPointerException.class);
			assertNull(ex.getMessage());
		}
		finally
		{
			shutdown();
		}
	}

	@Test
	public void exceptionWhileCheckingExistenceOfLink() throws Exception
	{
		startup(new DatabaseNotFoundException("Exception in test case."));
		final HGPersistentHandle handle = new UUIDPersistentHandle();
		try
		{
			storage.containsLink(handle);
		}
		catch (Exception ex)
		{
			final String expectedMessage = String
					.format("Failed to retrieve link with handle %s: com.sleepycat.je.DatabaseNotFoundException: (JE 5.0.34) Exception in test case.",
							handle.toString());
			assertExceptions(ex, HGException.class, expectedMessage);
		}
		finally
		{
			shutdown();
		}
	}
}
