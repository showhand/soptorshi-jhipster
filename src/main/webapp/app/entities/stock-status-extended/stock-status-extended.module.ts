import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    StockStatusDeleteDialogExtendedComponent,
    StockStatusDeletePopupComponentExtended,
    StockStatusDetailExtendedComponent,
    StockStatusExtendedComponent,
    stockStatusExtendedRoute,
    stockStatusPopupRouteExtended,
    StockStatusUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...stockStatusExtendedRoute, ...stockStatusPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StockStatusExtendedComponent,
        StockStatusDetailExtendedComponent,
        StockStatusUpdateExtendedComponent,
        StockStatusDeleteDialogExtendedComponent,
        StockStatusDeletePopupComponentExtended
    ],
    entryComponents: [
        StockStatusExtendedComponent,
        StockStatusUpdateExtendedComponent,
        StockStatusDeleteDialogExtendedComponent,
        StockStatusDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiStockStatusModuleExtended {}
