import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyMoneyCollection, SupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';
import { SupplyMoneyCollectionService } from './supply-money-collection.service';
import { SupplyMoneyCollectionComponent } from './supply-money-collection.component';
import { SupplyMoneyCollectionDetailComponent } from './supply-money-collection-detail.component';
import { SupplyMoneyCollectionUpdateComponent } from './supply-money-collection-update.component';
import { SupplyMoneyCollectionDeletePopupComponent } from './supply-money-collection-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyMoneyCollectionResolve implements Resolve<ISupplyMoneyCollection> {
    constructor(private service: SupplyMoneyCollectionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyMoneyCollection> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyMoneyCollection>) => response.ok),
                map((supplyMoneyCollection: HttpResponse<SupplyMoneyCollection>) => supplyMoneyCollection.body)
            );
        }
        return of(new SupplyMoneyCollection());
    }
}

export const supplyMoneyCollectionRoute: Routes = [
    {
        path: '',
        component: SupplyMoneyCollectionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyMoneyCollections'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyMoneyCollectionDetailComponent,
        resolve: {
            supplyMoneyCollection: SupplyMoneyCollectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyMoneyCollections'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyMoneyCollectionUpdateComponent,
        resolve: {
            supplyMoneyCollection: SupplyMoneyCollectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyMoneyCollections'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyMoneyCollectionUpdateComponent,
        resolve: {
            supplyMoneyCollection: SupplyMoneyCollectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyMoneyCollections'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyMoneyCollectionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyMoneyCollectionDeletePopupComponent,
        resolve: {
            supplyMoneyCollection: SupplyMoneyCollectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyMoneyCollections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
