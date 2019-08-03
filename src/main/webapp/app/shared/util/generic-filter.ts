import { Injectable, Pipe, PipeTransform } from '@angular/core';
import { ItemSubCategory } from 'app/shared/model/item-sub-category.model';

@Pipe({
    name: 'genericFilter'
})
@Injectable()
export class GenericFilter implements PipeTransform {
    transform(value: Object[], objectPropertyName: string, objectPropertyValue: any): any {
        return value.filter(val => val[objectPropertyName] === objectPropertyValue);
    }
}
