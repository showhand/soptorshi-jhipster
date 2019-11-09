/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyMoneyCollectionDetailComponent } from 'app/entities/supply-money-collection/supply-money-collection-detail.component';
import { SupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';

describe('Component Tests', () => {
    describe('SupplyMoneyCollection Management Detail Component', () => {
        let comp: SupplyMoneyCollectionDetailComponent;
        let fixture: ComponentFixture<SupplyMoneyCollectionDetailComponent>;
        const route = ({ data: of({ supplyMoneyCollection: new SupplyMoneyCollection(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyMoneyCollectionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyMoneyCollectionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyMoneyCollectionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyMoneyCollection).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
