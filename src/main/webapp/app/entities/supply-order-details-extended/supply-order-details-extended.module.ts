import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyOrderDetailsDeleteDialogExtendedComponent,
    SupplyOrderDetailsDeletePopupExtendedComponent,
    SupplyOrderDetailsExtendedComponent,
    supplyOrderDetailsExtendedRoute,
    supplyOrderDetailsPopupExtendedRoute,
    SupplyOrderDetailsUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyOrderDetailsExtendedRoute, ...supplyOrderDetailsPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyOrderDetailsExtendedComponent,
        SupplyOrderDetailsExtendedComponent,
        SupplyOrderDetailsUpdateExtendedComponent,
        SupplyOrderDetailsDeleteDialogExtendedComponent,
        SupplyOrderDetailsDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyOrderDetailsExtendedComponent,
        SupplyOrderDetailsUpdateExtendedComponent,
        SupplyOrderDetailsDeleteDialogExtendedComponent,
        SupplyOrderDetailsDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyOrderDetailsExtendedModule {}
