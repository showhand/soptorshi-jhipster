import { Pipe, PipeTransform } from '@angular/core';
import { SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';

@Pipe({ name: 'scmZoneManagerFilter' })
export class ScmZoneManagerFilterPipe implements PipeTransform {
    transform(value: number, args: SupplyZoneManager[]): string {
        return args.find(zoneManager => zoneManager.id == value).employeeFullName;
    }
}
