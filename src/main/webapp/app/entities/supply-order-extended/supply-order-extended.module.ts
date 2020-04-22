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
import { AccumulateOrderComponent } from 'app/entities/supply-order-extended/accumulate-order.component';

const ENTITY_STATES = [...supplyOrderExtendedRoute, ...supplyOrderPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyOrderExtendedComponent,
        SupplyOrderDetailExtendedComponent,
        SupplyOrderUpdateExtendedComponent,
        SupplyOrderDeleteDialogExtendedComponent,
        SupplyOrderDeletePopupExtendedComponent,
        AccumulateOrderComponent
    ],
    entryComponents: [
        SupplyOrderExtendedComponent,
        SupplyOrderUpdateExtendedComponent,
        SupplyOrderDeleteDialogExtendedComponent,
        SupplyOrderDeletePopupExtendedComponent,
        AccumulateOrderComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyOrderExtendedModule {}
