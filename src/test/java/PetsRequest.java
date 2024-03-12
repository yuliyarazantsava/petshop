import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetsRequest {
	private List<String> photoUrls;
	private String name;
	private double id;
	private Category category;
	private List<TagsItem> tags;
	private String status;
}
