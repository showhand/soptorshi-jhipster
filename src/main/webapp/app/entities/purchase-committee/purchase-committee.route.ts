import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchaseCommittee } from 'app/shared/model/purchase-committee.model';
import { PurchaseCommitteeService } from './purchase-committee.service';
import { PurchaseCommitteeComponent } from './purchase-committee.component';
import { PurchaseCommitteeDetailComponent } from './purchase-committee-detail.component';
import { PurchaseCommitteeUpdateComponent } from './purchase-committee-update.component';
import { PurchaseCommitteeDeletePopupComponent } from './purchase-committee-delete-dialog.component';
import { IPurchaseCommittee } from 'app/shared/model/purchase-committee.model';

@Injectable({ providedIn: 'root' })
export class PurchaseCommitteeResolve implements Resolve<IPurchaseCommittee> {
    constructor(private service: PurchaseCommitteeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPurchaseCommittee> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PurchaseCommittee>) => response.ok),
                map((purchaseCommittee: HttpResponse<PurchaseCommittee>) => purchaseCommittee.body)
            );
        }
        return of(new PurchaseCommittee());
    }
}

export const purchaseCommitteeRoute: Routes = [
    {
        path: '',
        component: PurchaseCommitteeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PurchaseCommitteeDetailComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PurchaseCommitteeUpdateComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PurchaseCommitteeUpdateComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseCommitteePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PurchaseCommitteeDeletePopupComponent,
        resolve: {
            purchaseCommittee: PurchaseCommitteeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseCommittees'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
