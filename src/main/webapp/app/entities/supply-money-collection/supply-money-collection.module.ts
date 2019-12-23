import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyMoneyCollectionComponent,
    SupplyMoneyCollectionDetailComponent,
    SupplyMoneyCollectionUpdateComponent,
    SupplyMoneyCollectionDeletePopupComponent,
    SupplyMoneyCollectionDeleteDialogComponent,
    supplyMoneyCollectionRoute,
    supplyMoneyCollectionPopupRoute
} from './';

const ENTITY_STATES = [...supplyMoneyCollectionRoute, ...supplyMoneyCollectionPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyMoneyCollectionComponent,
        SupplyMoneyCollectionDetailComponent,
        SupplyMoneyCollectionUpdateComponent,
        SupplyMoneyCollectionDeleteDialogComponent,
        SupplyMoneyCollectionDeletePopupComponent
    ],
    entryComponents: [
        SupplyMoneyCollectionComponent,
        SupplyMoneyCollectionUpdateComponent,
        SupplyMoneyCollectionDeleteDialogComponent,
        SupplyMoneyCollectionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyMoneyCollectionModule {}
