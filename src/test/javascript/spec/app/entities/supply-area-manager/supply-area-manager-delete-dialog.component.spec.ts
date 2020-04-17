/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaManagerDeleteDialogComponent } from 'app/entities/supply-area-manager/supply-area-manager-delete-dialog.component';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager/supply-area-manager.service';

describe('Component Tests', () => {
    describe('SupplyAreaManager Management Delete Component', () => {
        let comp: SupplyAreaManagerDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyAreaManagerDeleteDialogComponent>;
        let service: SupplyAreaManagerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaManagerDeleteDialogComponent]
            })
                .overrideTemplate(SupplyAreaManagerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyAreaManagerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyAreaManagerService);
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
