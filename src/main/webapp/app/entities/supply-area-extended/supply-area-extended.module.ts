import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyAreaDeleteDialogExtendedComponent,
    SupplyAreaDeletePopupExtendedComponent,
    SupplyAreaDetailExtendedComponent,
    SupplyAreaExtendedComponent,
    supplyAreaExtendedRoute,
    supplyAreaPopupExtendedRoute,
    SupplyAreaUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyAreaExtendedRoute, ...supplyAreaPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyAreaExtendedComponent,
        SupplyAreaDetailExtendedComponent,
        SupplyAreaUpdateExtendedComponent,
        SupplyAreaDeleteDialogExtendedComponent,
        SupplyAreaDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyAreaExtendedComponent,
        SupplyAreaUpdateExtendedComponent,
        SupplyAreaDeleteDialogExtendedComponent,
        SupplyAreaDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyAreaExtendedModule {}
