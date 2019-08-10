/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { StockStatusUpdateComponent } from 'app/entities/stock-status/stock-status-update.component';
import { StockStatusService } from 'app/entities/stock-status/stock-status.service';
import { StockStatus } from 'app/shared/model/stock-status.model';

describe('Component Tests', () => {
    describe('StockStatus Management Update Component', () => {
        let comp: StockStatusUpdateComponent;
        let fixture: ComponentFixture<StockStatusUpdateComponent>;
        let service: StockStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockStatusUpdateComponent]
            })
                .overrideTemplate(StockStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new StockStatus(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stockStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new StockStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.stockStatus = entity;
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
