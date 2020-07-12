/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyMoneyCollectionUpdateComponent } from 'app/entities/supply-money-collection/supply-money-collection-update.component';
import { SupplyMoneyCollectionService } from 'app/entities/supply-money-collection/supply-money-collection.service';
import { SupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';

describe('Component Tests', () => {
    describe('SupplyMoneyCollection Management Update Component', () => {
        let comp: SupplyMoneyCollectionUpdateComponent;
        let fixture: ComponentFixture<SupplyMoneyCollectionUpdateComponent>;
        let service: SupplyMoneyCollectionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyMoneyCollectionUpdateComponent]
            })
                .overrideTemplate(SupplyMoneyCollectionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SupplyMoneyCollectionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyMoneyCollectionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyMoneyCollection(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyMoneyCollection = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SupplyMoneyCollection();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.supplyMoneyCollection = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
