package component.resource.reference;

import component.resource.extractor.Extractor;

public class PosixResourceReferenceExtractor implements Extractor<String, ResourceReference> {

    @Override
    public ResourceReference extract(String spec) {
        int indexOf = spec.indexOf(":");
        
        if (indexOf == -1) {
            return new ResourceReference(ResourceReference.ANY_TYPE, spec);
        }
        
        return new ResourceReference(spec.substring(0, indexOf), spec.substring(indexOf + 1));
    }
    
}
