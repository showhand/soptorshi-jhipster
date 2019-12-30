/* tslint:disable max-line-length */
import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {HttpResponse} from '@angular/common/http';
import {of} from 'rxjs';

import {SoptorshiTestModule} from '../../../test.module';
import {StockInProcessUpdateComponent} from 'app/entities/stock-in-process/stock-in-process-update.component';
import {StockInProcessService} from 'app/entities/stock-in-process/stock-in-process.service';
import {StockInProcess} from 'app/shared/model/stock-in-process.model';

describe('Component Tests', () => {
    describe('StockInProcess Management Update Component', () => {
        let comp: StockInProcessUpdateComponent;
        let fixture: ComponentFixture<StockInProcessUpdateComponent>;
        let service: StockInProcessService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [StockInProcessUpdateComponent]
            })
                .overrideTemplate(StockInProcessUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockInProcessUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockInProcessService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockInProcess(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockInProcess = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockInProcess();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockInProcess = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
