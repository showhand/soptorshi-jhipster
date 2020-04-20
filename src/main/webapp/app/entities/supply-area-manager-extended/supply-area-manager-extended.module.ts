import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyAreaManagerDeleteDialogExtendedComponent,
    SupplyAreaManagerDeletePopupExtendedComponent,
    SupplyAreaManagerDetailExtendedComponent,
    SupplyAreaManagerExtendedComponent,
    supplyAreaManagerExtendedRoute,
    supplyAreaManagerPopupExtendedRoute,
    SupplyAreaManagerUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyAreaManagerExtendedRoute, ...supplyAreaManagerPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyAreaManagerExtendedComponent,
        SupplyAreaManagerDetailExtendedComponent,
        SupplyAreaManagerUpdateExtendedComponent,
        SupplyAreaManagerDeleteDialogExtendedComponent,
        SupplyAreaManagerDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyAreaManagerExtendedComponent,
        SupplyAreaManagerUpdateExtendedComponent,
        SupplyAreaManagerDeleteDialogExtendedComponent,
        SupplyAreaManagerDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyAreaManagerExtendedModule {}
