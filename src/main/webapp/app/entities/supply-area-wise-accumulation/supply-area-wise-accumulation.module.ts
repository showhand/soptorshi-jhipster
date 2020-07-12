import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyAreaWiseAccumulationComponent,
    SupplyAreaWiseAccumulationDeleteDialogComponent,
    SupplyAreaWiseAccumulationDeletePopupComponent,
    SupplyAreaWiseAccumulationDetailComponent,
    supplyAreaWiseAccumulationPopupRoute,
    supplyAreaWiseAccumulationRoute,
    SupplyAreaWiseAccumulationUpdateComponent
} from './';

const ENTITY_STATES = [...supplyAreaWiseAccumulationRoute, ...supplyAreaWiseAccumulationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyAreaWiseAccumulationComponent,
        SupplyAreaWiseAccumulationDetailComponent,
        SupplyAreaWiseAccumulationUpdateComponent,
        SupplyAreaWiseAccumulationDeleteDialogComponent,
        SupplyAreaWiseAccumulationDeletePopupComponent
    ],
    entryComponents: [
        SupplyAreaWiseAccumulationComponent,
        SupplyAreaWiseAccumulationUpdateComponent,
        SupplyAreaWiseAccumulationDeleteDialogComponent,
        SupplyAreaWiseAccumulationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyAreaWiseAccumulationModule {}
