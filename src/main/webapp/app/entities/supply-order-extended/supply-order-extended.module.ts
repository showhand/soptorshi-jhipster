import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyOrderDeleteDialogExtendedComponent,
    SupplyOrderDeletePopupExtendedComponent,
    SupplyOrderDetailExtendedComponent,
    SupplyOrderExtendedComponent,
    supplyOrderExtendedRoute,
    supplyOrderPopupExtendedRoute,
    SupplyOrderUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyOrderExtendedRoute, ...supplyOrderPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyOrderExtendedComponent,
        SupplyOrderDetailExtendedComponent,
        SupplyOrderUpdateExtendedComponent,
        SupplyOrderDeleteDialogExtendedComponent,
        SupplyOrderDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyOrderExtendedComponent,
        SupplyOrderUpdateExtendedComponent,
        SupplyOrderDeleteDialogExtendedComponent,
        SupplyOrderDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyOrderExtendedModule {}
