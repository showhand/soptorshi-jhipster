import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyOrderDetailsDeleteDialogExtendedComponent,
    SupplyOrderDetailsDeletePopupExtendedComponent,
    SupplyOrderDetailsDetailExtendedComponent,
    SupplyOrderDetailsExtendedComponent,
    supplyOrderDetailsExtendedRoute,
    supplyOrderDetailsPopupExtendedRoute,
    SupplyOrderDetailsUpdateExtendedComponent
} from './';
import { SupplyOrderAddProductComponent } from 'app/entities/supply-order-details-extended/supply-order-add-product.component';

const ENTITY_STATES = [...supplyOrderDetailsExtendedRoute, ...supplyOrderDetailsPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyOrderDetailsExtendedComponent,
        SupplyOrderDetailsDetailExtendedComponent,
        SupplyOrderDetailsUpdateExtendedComponent,
        SupplyOrderDetailsDeleteDialogExtendedComponent,
        SupplyOrderDetailsDeletePopupExtendedComponent,
        SupplyOrderAddProductComponent
    ],
    entryComponents: [
        SupplyOrderDetailsExtendedComponent,
        SupplyOrderDetailsUpdateExtendedComponent,
        SupplyOrderDetailsDeleteDialogExtendedComponent,
        SupplyOrderDetailsDeletePopupExtendedComponent,
        SupplyOrderAddProductComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyOrderDetailsExtendedModule {}
