package component.resource.reference;

import component.resource.extractor.Extractor;

public class UncResourceReferenceExtractor implements Extractor<String, ResourceReference> {

    @Override
    public ResourceReference extract(String spec) {
        if (isAbsolute(spec)) {
            return new ResourceReference(ResourceReference.ANY_TYPE, spec);
        }
        
        int indexOf = spec.indexOf(":");
        if (indexOf == -1) {
            return new ResourceReference(ResourceReference.ANY_TYPE, spec);
        }
        
        return new ResourceReference(spec.substring(0, indexOf), spec.substring(indexOf + 1));
    }
    
    private boolean isAbsolute(String spec) {
        return spec.length() > 2 && spec.charAt(1) == ':' && spec.charAt(2) == '\\';
    }
    
}
