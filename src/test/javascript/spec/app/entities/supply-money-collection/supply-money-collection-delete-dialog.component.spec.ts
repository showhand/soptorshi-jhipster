/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyMoneyCollectionDeleteDialogComponent } from 'app/entities/supply-money-collection/supply-money-collection-delete-dialog.component';
import { SupplyMoneyCollectionService } from 'app/entities/supply-money-collection/supply-money-collection.service';

describe('Component Tests', () => {
    describe('SupplyMoneyCollection Management Delete Component', () => {
        let comp: SupplyMoneyCollectionDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyMoneyCollectionDeleteDialogComponent>;
        let service: SupplyMoneyCollectionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyMoneyCollectionDeleteDialogComponent]
            })
                .overrideTemplate(SupplyMoneyCollectionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyMoneyCollectionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyMoneyCollectionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
